package com.mictlan.mvc.models

import com.mictlan.math.geometry.IVector
import com.mictlan.math.graphs.Graph
import com.mictlan.mvc.GlobalSettings
import com.mictlan.poly2tri.Poly2Tri
import com.mictlan.poly2tri.geometry.polygon.Polygon
import com.mictlan.poly2tri.geometry.polygon.PolygonVertex
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle
import java.util.*
import kotlin.collections.HashMap


class Map {
    var goal: Graph? = null
    var start: Graph? = null
    var mapGeometry: Polygon? = null
    val buffer: MutableCollection<IVector> = mutableListOf()
    var graphs: MutableMap<Int, Graph> = HashMap()

    fun pushBuffer() {
        if(buffer.count() < 3) {
            println("Edit: Attemted to create polygon with ${buffer.count()}")
            return;
        }

        val shape = Polygon(buffer.map { PolygonVertex(it.x, it.y, it.z) })
        buffer.clear()

        if(mapGeometry == null){
            mapGeometry = shape
            println("Edit: Map created")
        }else{
            mapGeometry?.addHole(shape)
            println("Edit: Hole added")

        }

        Poly2Tri.triangulate(mapGeometry)
        genGraphTree()
    }

    fun genGraphTree(){
        graphs.clear()
        val open: MutableCollection<DelaunayTriangle> = mapGeometry!!.triangles!!.filter { it.isInterior }.toMutableList()
        val visited: MutableMap<DelaunayTriangle, Graph> = HashMap()

        while(open.isNotEmpty()){
            val current = open.first()
            val v = current.centroid()

            val graph = Graph( v, mutableListOf(), current)
            current.neighbors
                    .filter { it!=null && it.isInterior }
                    .forEach{ tr ->
                        if(visited.containsKey(tr)){
                            val other = visited[tr]!!
                            other.neighbors.add(graph)
                            graph.neighbors.add(other)
                        }
                    }
            graphs[graph.index] = graph

            open.remove(current)
            visited[current] = graph
        }

    }

    fun setGraphFromPosition(position: IVector): Graph?{
        val graphGoal = graphs.values.find{ it.triangle.isPointInside( position) } ?: return null
        return Graph( position, mutableListOf(graphGoal), graphGoal.triangle)
    }

    fun setGoal( goalPosition: IVector): Graph?{
        if(goal != null)graphs.remove(goal!!.index)
        goal = setGraphFromPosition( goalPosition)?: return null
        graphs[goal!!.index] = goal!!
        return goal
    }

    fun setStart( startPosition: IVector): Graph?{
        if(start != null)graphs.remove(start!!.index)
        start = setGraphFromPosition( startPosition)?: return null
        graphs[start!!.index] = start!!
        return start
    }

    fun findPath(): Boolean {
        if(goal == null && start == null) return false
        val open: LinkedList<Graph> = LinkedList()
        val visited: MutableMap<Int, Graph> = HashMap()
        open.add(start!!)
        var searchIndex = GlobalSettings.MaximumSearchCount
        while(open.isNotEmpty()){
            if(searchIndex <= 0) { println("Map: MaximumSearch triggered"); break}
            if(open.isEmpty()){ println("Map: open graphs is empty"); break }
            val current = open.first()
            open.removeIf { it.index == current.index}
            current.neighbors
                    .filter{ n ->
                        if(!visited.containsKey(n.index))return true;
                        return false;
                    }
                    .sortedByDescending { it.pathCost }
                    .forEach{ open.push(it)}

            searchIndex--
        }
        return goal!!.parent != null
    }
}