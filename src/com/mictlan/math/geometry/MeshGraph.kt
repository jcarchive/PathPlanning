package com.mictlan.math.geometry

class MeshGraph(a: IVector, b: IVector, c: IVector): ITriangle {
    private val vertex: Collection<IVector>
    override val edgeAB: ILine = Line(a,b)
    override val edgeBC: ILine = Line(b,c)
    override val edgeCA: ILine = Line(c,a)
    override val a: IVector
        get() = vertex.elementAt(0)
    override val b: IVector
        get() = vertex.elementAt(1)
    override val c: IVector
        get() = vertex.elementAt(2)

    init{
        val vCollection: MutableList<IVector> = mutableListOf(a,b,c)
        vCollection.sortedBy { it.x }
        var first = vCollection[0]
        val crossResult = ((vCollection[1] - vCollection[0]) cross ( vCollection[2] - vCollection[1])).z
        if(crossResult > 0.0){
            val aux = vCollection[1]
            vCollection[1] = vCollection[2]
            vCollection[2] = aux
        }
        vertex = vCollection
    }
    val neighbors: MutableCollection<ITriangle> = mutableListOf()

}