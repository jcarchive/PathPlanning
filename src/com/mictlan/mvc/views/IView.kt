package com.mictlan.mvc.views

import com.mictlan.math.geometry.IVector
import com.mictlan.structures.INode
import processing.core.PGraphics

interface IView: INode<IView> {

    abstract val canvas: PGraphics

    fun draw(canvas: PGraphics)
    fun update()
    fun toggleLayer(index: Int) { }
}