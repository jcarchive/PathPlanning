package com.mictlan.math.graphs

import com.mictlan.math.geometry.IVector

abstract class Graph<T: IGraph<T>>(override var neighbors: MutableCollection<T>): IGraph<T>{
    override var parent: T? = null
    override var heuristic: Double = Double.MAX_VALUE
    override var stepCost: Double = Double.MAX_VALUE
    override val pathCost: Double
        get() {
            var current: IGraph<T>? = this
            val visited: MutableMap<Int, IGraph<T>> = mutableMapOf()
            var sum = 0.0
            while(current != null){
                if(visited.containsKey(current.index)){
                    println("Recursive error: circle reference")
                    break
                }
                //Shortbreak "infinity value"
                if(current.stepCost == Double.MAX_VALUE) return Double.MAX_VALUE
                sum += current.stepCost
                visited[current.index] = current
                current = current.parent
            }
            return sum
        }
    override val weight: Double
        get() { return pathCost + heuristic}
    override val index: Int = count++

    companion object{
        var count: Int = 0
        fun <T: IGraph<T>> buildPath(end: T ): Collection<T>{
            val path: MutableCollection<T> = mutableListOf()
            var current: T? = end
            val visited: MutableMap<Int, T> = mutableMapOf()
            while(current != null){
                if(visited.containsKey(current.index)){
                    println("Circle Reference")
                    break
                }
                visited[current.index] = current
                path.add(current)

                current = current.parent
            }

            return path.reversed()
        }
    }

}

