package com.mictlan.math.geometry

interface IVector{
    val x: Double
    val y: Double
    val z: Double

    operator fun get(i: Int): Double

    fun translate(translation: IVector): IVector
    fun rotate(angle: Double, axis: IVector): IVector
    fun magnitude(): Double
    fun normalize(): IVector

    infix fun dist(vector: IVector): Double
    infix fun cross(vector: IVector): IVector
    infix fun dot(vector: IVector): Double
    infix fun angle(vector: IVector): Double

    operator fun plus(vector: IVector): IVector
    operator fun minus(vector: IVector): IVector
    operator fun times(k: Double): IVector
    operator fun div(k: Double): IVector

}