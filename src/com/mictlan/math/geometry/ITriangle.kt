package com.mictlan.math.geometry

interface ITriangle {
    val a: IVector
    val b: IVector
    val c: IVector

    val edgeAB: ILine
    val edgeBC: ILine
    val edgeCA: ILine
}