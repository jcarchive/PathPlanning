package com.mictlan.math.geometry

class Line(override val start: IVector, override val end: IVector) : ILine {
    override fun getPoint(p: Double): IVector {
        return (end - start)/p + start
    }
}