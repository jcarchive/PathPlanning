package com.mictlan.math.geometry

import com.mictlan.math.geometry.triangulation.MeshTree
import com.mictlan.poly2tri.geometry.polygon.PolygonVertex
import com.mictlan.poly2tri.triangulation.TriangulationContext
import com.mictlan.poly2tri.triangulation.TriangulationMode
import com.mictlan.poly2tri.triangulation.TriangulationPoint
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle
import kotlin.collections.Collection
import kotlin.collections.List

class Polygon {
        val vertex: MutableCollection<IVector> = mutableListOf()
        var holes: MutableCollection<Polygon> = mutableListOf()
        var mesh: MeshTree? = null

        var triangles: MutableCollection<DelaunayTriangle> = mutableListOf()

        val last: PolygonVertex? = null

        fun addPoints(points: Collection<IVector>) {
            if (points.size < 3) throw IndexOutOfBoundsException("Polygon can't be created with less than 3 points")
            vertex.addAll(points.map { v -> TriangulationPoint( v.x, v.y, v.z) })
        }

        fun getTriangulationMode(): TriangulationMode {
            return TriangulationMode.POLYGON
        }

        fun pointCount(): Int {
            return vertex.size
        }

        fun addHole(poly: Polygon) {
            holes.add(poly)
        }

        fun getPoints(): Collection<IVector> {
            return vertex
        }

        fun addTriangle(t: DelaunayTriangle) {
            triangles.add(t)
        }

        fun addTriangles(list: List<DelaunayTriangle>) {
            triangles.addAll(list)
        }

        fun clearTriangulation() {
            triangles.clear()
        }

        fun triangulate(tcx: TriangulationContext<*>) {
            /*val hint =vertex.size + holes.sumBy { s -> s.pointCount() }

            val uniquePts = HashMap<TriangulationPoint, TriangulationPoint>(hint)
            TriangulationPoint.extractUniques(uniquePts, vertex)
            holes.forEach { hole -> TriangulationPoint.extractUniques(uniquePts, hole.vertex) }
            triangles.clear()

            // Outer constraints
            vertex.zipWithNext().forEach{ c -> tcx.newConstraint(c.first, c.second)}
            tcx.newConstraint(vertex.first(), vertex.last())

            // Hole constraints
            for (p in holes) {
                for (c in vertex.zipWithNext()) {
                    tcx.newConstraint(c.first, c.second)
                }
                tcx.newConstraint(p.vertex.first(), p.vertex.last())
            }

            tcx.addPoints(uniquePts.keys)*/
        }

}