package com.mictlan.math.graphs

interface  IGraph<T: IGraph<T>>{
    val index:Int
    val weight: Double
    var pathCost: Double
    var heuristic: Double

    var parent: T?
    var neighbors: MutableCollection<T>

    fun calculateHeuristic( parent: T, goal: T): Double
    fun calculatePathcost(parent: T, goal: T): Double
}