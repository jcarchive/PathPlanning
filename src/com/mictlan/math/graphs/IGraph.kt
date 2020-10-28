package com.mictlan.math.graphs

interface  IGraph<T: IGraph<T>> {
    val index: Int
    val weight: Double
    var stepCost: Double
    var heuristic: Double

    val pathCost: Double

    var parent: T?
    var neighbors: MutableCollection<T>

    fun calculateHeuristic(goal: T): Double
    fun calculateStepCost(parent: T): Double
}