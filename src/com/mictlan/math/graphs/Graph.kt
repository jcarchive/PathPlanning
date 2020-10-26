package com.mictlan.math.graphs

import com.mictlan.math.geometry.IVector
import com.mictlan.poly2tri.triangulation.delaunay.DelaunayTriangle

class Graph(val point: IVector, var neighbors: MutableCollection<Graph>, val triangle: DelaunayTriangle){
    var parent: Graph? = null
    val pathCost: Double
        get() = this.calculatePathCost()
    var weigth: Double= Double.MAX_VALUE
    val index = count++

    companion object{
        private var count = 0
    }
    override fun equals(other: Any?): Boolean {
        if( other is Graph){
            return other.point == point
        }
        return super.equals(other)
    }

    fun calculatePathCost(): Double{
        var current: Graph? = this
        var sum = 0.0
        while(current != null){
            sum += if(current.parent == null) 0.0; else (current.point  - current.parent!!.point).magnitude()
            current = current.parent
        }
        return sum
    }
}
