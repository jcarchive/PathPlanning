package com.mictlan.math.geometry

interface IMatrix {
    fun valuesSequence(): Sequence<Double>
    operator fun get(i: Int, j: Int): Double
    operator fun set(i: Int, j: Int, value: Double)

    operator fun times(k: Double): IMatrix
    operator fun div(k: Double): IMatrix = times(1 / k)

    operator fun times(matrix: IMatrix): IMatrix
    operator fun times(vector: IVector): IVector

    operator fun plus(matrix: IMatrix): IMatrix
    operator fun minus(matrix: IMatrix): IMatrix
}