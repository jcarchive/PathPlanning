package com.mictlan.math.geometry

interface ILine {
    fun getPoint(p: Double): IVector

    val start: IVector
    val end: IVector
}