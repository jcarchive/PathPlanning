package com.mictlan.math.geometry

import java.lang.IndexOutOfBoundsException
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector(override val x: Double =  0.0, override val y: Double = 0.0, override val z: Double = 0.0) : IVector{

    override operator fun get(i: Int): Double{
        return when (i) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IndexOutOfBoundsException()
        }
    }

    override fun translate(translation: IVector): IVector {
        return this + translation
    }

    override fun rotate(angle: Double, axis: IVector): IVector {
        return this*cos(angle) + (axis cross this)*sin(angle) + axis*(axis dot this)*(1 - cos(angle))
    }

    override fun magnitude(): Double {
        return sqrt(x*x + y*y + z*z)
    }

    override fun normalize(): IVector {
        val m = this.magnitude()
        return Vector(x/m, y/m, z/m)
    }


    override fun dist(vector: IVector): Double{
        return (vector - this).magnitude()
    }

    override fun cross(vector: IVector): IVector {
        return Vector(y*vector.z - z*vector.y, z*vector.x - x*vector.z, x*vector.y - y*vector.x)
    }

    override fun dot(vector: IVector): Double{
        return x*vector.x + y*vector.y + z*vector.z
    }

    override fun angle(vector: IVector): Double {
        val r = this dot vector
        return asin(r / (magnitude() * vector.magnitude()))
    }

    override operator fun plus(vector: IVector): IVector{
        return Vector(x + vector.x, y + vector.y, z + vector.z)
    }

    override operator fun minus(vector: IVector): IVector{
        return Vector(x - vector.x, y - vector.y, z - vector.z)
    }

    override fun times(k: Double): IVector {
        return Vector(x*k, y*k, z*k)
    }

    override fun div(k: Double): IVector {
        return Vector(x/k, y/k, z/k)
    }
}