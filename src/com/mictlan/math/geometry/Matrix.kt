package com.mictlan.math.geometry

class Matrix : IMatrix {
    private var values: DoubleArray = DoubleArray(9){_-> 0.0}

    override operator fun get(i: Int, j: Int): Double{
        return values[(i - 1)*3 + j - 1]
    }
    override operator fun set(i: Int, j: Int, value: Double){
        values[(i -1)*3 + (j - 1)] = value
    }
    override fun times(k: Double): IMatrix {
        val result = Matrix()
        values.forEachIndexed{i, v -> result.values[i] = v*k }
        return result
    }

    override fun times(matrix: IMatrix): IMatrix {
        val result = Matrix()
        for(i in 1..3)
            for(j in 1..3)
                for(k in 1..3){
                    result[i,j] += this[i,k]*matrix[k, j]
                }
        return result
    }

    override fun times(vector: IVector): IVector {
        val x = values.filterIndexed{ i,_ -> i in 0..2}.mapIndexed{i,v -> vector[i]*v}.sum()
        val y = values.filterIndexed{ i,_ -> i in 3..5 }.mapIndexed{ i, v -> vector[i]*v}.sum()
        val z = values.filterIndexed{ i,_ -> i in 6..8}.mapIndexed{i,v -> vector[i]*v}.sum()
        return Vector(x,y,z)
    }

    override fun plus(matrix: IMatrix): IMatrix {
        val result = Matrix()
        matrix.valuesSequence().forEachIndexed{ i, v -> result.values[i] = values[i] + v }
        return result
    }


    override fun minus(matrix: IMatrix): IMatrix {
        val result = Matrix()
        matrix.valuesSequence().forEachIndexed{ i, v -> result.values[i] = values[i] - v }
        return result
    }

    override fun valuesSequence(): Sequence<Double> {
        return values.asSequence()
    }

    override fun toString(): String {
        var s = ""
        for((i, v) in values.withIndex()){
            s += "$v "
            if((i + 1) % 3 == 0) s += '\n'
        }
        return s
    }
}