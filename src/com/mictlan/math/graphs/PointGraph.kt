package com.mictlan.math.graphs

import com.mictlan.math.geometry.IVector
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle

class PointGraph(val position: IVector, neighbors: MutableCollection<PointGraph>) : Graph<PointGraph>(neighbors) {
    companion object{
        fun buildPath( end: PointGraph): Collection<IVector>{
            val path: MutableCollection<IVector> = mutableListOf()
            var current: PointGraph? = end
            while(current != null){
                path.add(current.position)
                current = current.parent
            }

            return path.reversed()
        }
    }
    override fun calculateHeuristic(parent: PointGraph, goal: PointGraph): Double {
        return 0.0;
    }

    override fun calculatePathcost(parent: PointGraph, goal: PointGraph): Double {
        return parent.pathCost + (goal.position - position).magnitude()
    }

}