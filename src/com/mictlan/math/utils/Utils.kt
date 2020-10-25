package com.mictlan.math.utils

import kotlin.math.abs
import kotlin.math.min

const val EPSILON: Double = 10.0

fun Double.Companion.equalsEpsilon( a: Double, b: Double, epsilon: Double = EPSILON): Boolean{
    val absA = abs(a)
    val absB = abs(b)
    val absA_B= abs(a - b)

    if(a == b) return true
    if ( a == 0.0 || b == 0.0 || absA + absB < Double.MIN_VALUE) return absA_B < (epsilon * Double.MIN_VALUE)
    return absA_B / min(absA + absB, Double.MAX_VALUE) < epsilon
}