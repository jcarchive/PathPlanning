package com.mictlan.structures

interface INode<T> {
    val guid: String

    fun addChild( child: T)
    fun addChildren(children: Collection<T>)

}