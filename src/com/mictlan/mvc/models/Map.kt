package com.mictlan.mvc.models

import com.mictlan.math.geometry.ILine
import com.mictlan.math.geometry.IVector
import com.mictlan.math.graphs.*
import com.mictlan.math.graphs.Graph.Companion.buildPath
import com.mictlan.math.legacy.geometry.*
import com.mictlan.poly2tri.Poly2Tri
import com.mictlan.poly2tri.geometry.polygon.Polygon
import com.mictlan.poly2tri.geometry.polygon.PolygonVertex
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle
import processing.core.PVector
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.max
import kotlin.math.min


class Map {
    var goal: CellGraph? = null
    var start: CellGraph? = null
    var mapGeometry: Polygon? = null
    val buffer: LinkedList<IVector> = LinkedList()
    var graphs: MutableMap<Int, CellGraph> = HashMap()

    var pathGraphs: MutableMap<Int, PointGraph> = HashMap()
    var path: Collection<IVector> = mutableListOf()
    val r = 10f;

    fun pushBuffer() {
        if(buffer.count() < 3) {
            println("Edit: Attempted to create polygon with ${buffer.count()}")
            return;
        }
        var obstacle = mutableListOf<IPoint>();
        var i = 0;
        var iter = buffer.listIterator();
        while( iter.hasNext()){
            i++
            var b: IPoint
            var a: IPoint
            var c: IPoint
            if (!iter.hasPrevious()) a = Point(buffer.last.x.toFloat(), buffer.last.y.toFloat() )else {
                val ap = iter.previous()
                a = Point(ap.x.toFloat(), ap.y.toFloat());
                iter.next()
            }
            val bp = iter.next()
            b = Point(bp.x.toFloat(), bp.y.toFloat());
            if (!iter.hasNext()) c = obstacle.first() else {
                val cp = iter.next()
                c =  Point(cp.x.toFloat(), cp.y.toFloat());
                iter.previous()
            }
            val lineAB = Line(a, b)
            val lineBC = Line(b, c)

            val nAB: PVector = lineAB.normal.mult(r)
            val nBC: PVector = lineBC.normal.mult(r)

            val eA = Point(a.asVector().copy().add(nAB))
            val eBa = Point(b.asVector().copy().add(nAB))
            val eBc = Point(b.asVector().copy().add(nBC))
            val eC = Point(c.asVector().copy().add(nBC))

            val extrudedLineAB = Line(eA, eBa)
            val extrudedLineBC = Line(eBc, eC)

            val p: LineIntersection? =  com.mictlan.math.legacy.geometry.ILine.extendedIntersection(extrudedLineAB, extrudedLineBC)

            if (IPoint.orientation(a, b, c) === PointOrientation.LIES_LEFT) {
                p?.point?.x?.let { Point(it, p.point.y) }?.let { obstacle.add(it)};
            } else {
                var m = IPoint.middlePoint(eBa, eBc).asVector()
                m = m.sub(b.asVector()).normalize().mult(r).add(b.asVector())
                obstacle.add(Point(eBa.x, eBa.y));
                obstacle.add(Point(m.x, m.y));
                obstacle.add(Point(eBc.x, eBc.y));
            }
        }

        val shape = Polygon( obstacle.map { PolygonVertex(it.x.toDouble(), it.y.toDouble(), 0.0) })
        buffer.clear()

        if(mapGeometry == null){
            mapGeometry = shape
            println("Edit: Map created")
        }else{
            Poly2Tri.triangulate(shape)
            mapGeometry?.addHole(shape)
            println("Edit: Hole added")

        }

        Poly2Tri.triangulate(mapGeometry)
        genCellGraphTree()
    }

    fun genCellGraphTree(){
        goal = null
        start = null
        graphs.clear()
        path = mutableListOf()
        pathGraphs.clear()
        val open: MutableCollection<DelaunayTriangle> = mapGeometry!!.triangles!!.filter { it.isInterior }.toMutableList()
        val visited: MutableMap<DelaunayTriangle, CellGraph> = HashMap()

        while(open.isNotEmpty()){
            val current = open.first()
            val v = current.centroid()

            val graph = CellGraph( v, current, mutableListOf())
            current.neighbors
                    .filter { it!=null && it.isInterior }
                    .forEach{ tr ->
                        if(visited.containsKey(tr)){
                            val other = visited[tr]!!
                            val line: ILine = DelaunayTriangle.getEdgeBetween(other.cell, graph.cell)
                            val transferGraph = CellGraph( line.getPoint(2.0), graph.cell, mutableListOf( graph, other))
                            graph.neighbors.add(transferGraph)
                            other.neighbors.add(transferGraph)
                        }
                    }
            graphs[graph.index] = graph

            open.remove(current)
            visited[current] = graph
        }

    }

    fun setCellGraphFromPosition(position: IVector): CellGraph?{
        val nearestGraph = graphs.values.find{ it.cell.isPointInside( position) } ?: return null
        val newGraph = CellGraph( position, nearestGraph.cell, mutableListOf(nearestGraph))
        nearestGraph.neighbors.add(newGraph)
        return newGraph
    }

    fun setGoal( goalPosition: IVector): CellGraph?{
        if(goal != null){
            graphs.remove(goal!!.index)
            goal!!.neighbors.forEach { neighbor -> neighbor.neighbors.remove(goal!!) }
        }
        goal = setCellGraphFromPosition( goalPosition)?: return null
        goal!!.stepCost = Double.MIN_VALUE
        goal!!.heuristic = Double.MIN_VALUE

        graphs[goal!!.index] = goal!!
        return goal
    }

    fun setStart( startPosition: IVector): CellGraph?{
        if(start != null) {
            graphs.remove(start!!.index)
            start!!.neighbors.forEach { neighbor -> neighbor.neighbors.remove(start!!) }
        }
        start = setCellGraphFromPosition( startPosition)?: return null
        start!!.stepCost = 0.0
        start!!.heuristic = 0.0
        graphs[start!!.index] = start!!
        return start
    }

    fun findPath(): Boolean {
        if (goal == null || start == null) return false
        val search = Astar(start!!, goal!!)
        return when(search.search(10000)){
            Search.SearchResultStatus.MAX_ITERATION_REACHED -> { println("Map: Path search, max iteration reached"); false
            }
            Search.SearchResultStatus.NOT_FOUND -> {println("Map: Path search, path not found"); false
            }
            Search.SearchResultStatus.FOUND -> {
                println("Map: Path search, path found");
                path = Graph.buildPath(goal!!).map { g -> g.position }
                true
            }
        }
    }

    fun pathSmoothing(){
        pathGraphs.clear()
        var graphs = path.map { p -> PointGraph(p,  mutableListOf()) }
        val start = graphs.first()
        val end = graphs.last()

        for ((index, current) in graphs.withIndex()) {
            pathGraphs[current.index] = current
            for (cmp in graphs.subList(index, graphs.size)) {
                if(cmp == current) continue
                if(!isInCollision(current.position, cmp.position)){
                    current.neighbors.add(cmp)
                }
            }
        }
        val search = Dijkstra(start, end)
        if(search.search() == Search.SearchResultStatus.FOUND){
            println("Mat: Pathsmoothing success")
            path = buildPath(end).map { g -> g.position }
        }else{
            println("Mat: Path smoothing failed")
        }
    }

    fun splitPath(){
        val resultPath = mutableListOf<IVector>()
        for ((a, b) in path.zipWithNext()) {
            resultPath.add(a)
            resultPath.add((a + b)/2.0)
        }

        resultPath.add(path.last())
        path = resultPath
    }


    fun isInCollision(p: IVector, q: IVector): Boolean{
        if(mapGeometry?.holes == null) return false;
        for (hole in mapGeometry!!.holes) {
            for (triangle in hole.triangles) {
                if(intersection( p, q, triangle))return true
            }
        }
        return false
    }

    fun intersection(p:IVector, q:IVector, triangle: DelaunayTriangle): Boolean{
        if(triangle.isPointInside(p) || triangle.isPointInside(q))return true;

        if( doIntersect( p, q, triangle.points[0] , triangle.points[1]) ||
            doIntersect( p, q, triangle.points[1] , triangle.points[2]) ||
            doIntersect( p, q, triangle.points[2] , triangle.points[0]))
            return true
        return false
    }

    fun onSegment(p: IVector, q: IVector, r: IVector): Boolean {
        return q.x <= max(p.x, r.x) && q.x >= min(p.x, r.x) && q.y <= max(p.y, r.y) && q.y >= min(p.y, r.y)
    }

    fun orientation(p: IVector, q: IVector, r: IVector): Int {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        val `val`: Double = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y)
        if (`val` == 0.0) return 0 // colinear
        return if (`val` > 0) 1 else 2 // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    fun doIntersect(p1: IVector, q1: IVector, p2: IVector, q2: IVector): Boolean {
        // Find the four orientations needed for general and
        // special cases
        val o1 = orientation(p1, q1, p2)
        val o2 = orientation(p1, q1, q2)
        val o3 = orientation(p2, q2, p1)
        val o4 = orientation(p2, q2, q1)

        // General case
        if (o1 != o2 && o3 != o4) return true

        // Special Cases
        // p1, q1 and p2 are colinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(p1, p2, q1)) return true

        // p1, q1 and q2 are colinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(p1, q2, q1)) return true

        // p2, q2 and p1 are colinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(p2, p1, q2)) return true

        // p2, q2 and q1 are colinear and q1 lies on segment p2q2
        return o4 == 0 && onSegment(p2, q1, q2)
        // Doesn't fall in any of the above cases
    }
}