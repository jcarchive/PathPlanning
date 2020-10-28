package com.mictlan.math.graphs

import com.mictlan.math.geometry.IVector
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle

class CellGraph(val position: IVector, val cell: DelaunayTriangle, neighbors: MutableCollection<CellGraph>) : Graph<CellGraph>(neighbors) {

    override fun calculateHeuristic(goal: CellGraph): Double {
        return (goal.position - position).magnitude()
    }

    override fun calculateStepCost(parent: CellGraph): Double {
        return (parent.position - position).magnitude()
    }


    override fun equals(other: Any?): Boolean {
        if(other is CellGraph){
            return other.index == this.index
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = position.hashCode()
        result = 31 * result + cell.hashCode()
        return result
    }
}
