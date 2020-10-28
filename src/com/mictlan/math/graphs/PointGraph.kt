package com.mictlan.math.graphs

import com.mictlan.math.geometry.IVector

class PointGraph(val position: IVector, neighbors: MutableCollection<PointGraph>, private val pathPositionWeight: Double = 0.0) : Graph<PointGraph>(neighbors) {

    override fun calculateHeuristic(goal: PointGraph): Double {
        val mag = (goal.position - position).magnitude();
        return heuristic
    }

    override fun calculateStepCost(parent: PointGraph): Double {
        return (parent.position - position).magnitude()
    }

}