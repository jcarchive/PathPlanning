package com.mictlan.math.graphs

abstract class Graph<T: IGraph<T>>(override var neighbors: MutableCollection<T>): IGraph<T>{
    override var parent: T? = null
    override var pathCost: Double = Double.MAX_VALUE
    override var heuristic: Double = Double.MAX_VALUE
    override val weight: Double
        get() { return pathCost + heuristic}

    override val index: Int = count++
    companion object {
        var count: Int = 0
    }

}

