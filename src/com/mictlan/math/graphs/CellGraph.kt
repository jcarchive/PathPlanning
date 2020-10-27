package com.mictlan.math.graphs

import com.mictlan.math.geometry.IVector
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle

class CellGraph(val position: IVector, val cell: DelaunayTriangle, neighbors: MutableCollection<CellGraph>) : Graph<CellGraph>(neighbors) {

    companion object{
        fun buildPath( end: CellGraph): Collection<IVector>{
            val path: MutableCollection<IVector> = mutableListOf()
            var current: CellGraph? = end
            while(current != null){
                path.add(current.position)
                current = current.parent
            }

            return path.reversed()
        }
    }
    override fun calculateHeuristic(parent: CellGraph, goal: CellGraph): Double {
        return (position  - goal.position).magnitude()
    }

    override fun calculatePathcost(parent: CellGraph, goal: CellGraph): Double {
        return parent.pathCost + (position - parent.position).magnitude()
    }


    override fun equals(other: Any?): Boolean {
        if(other is CellGraph){
            return other.index == this.index
        }
        return super.equals(other)
    }
}
