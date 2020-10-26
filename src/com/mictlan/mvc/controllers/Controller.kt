package com.mictlan.mvc.controllers

import com.mictlan.mvc.Frame
import com.mictlan.mvc.views.IView
import com.mictlan.structures.INode
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent

open class Controller<T>(val model: T, val view: IView, override val guid: String): IController, INode<IController> {
    val context = Frame.instance
    var children: MutableCollection<IController> = mutableListOf()

    companion object{
        fun <T: IController> attachContext(applet: PApplet) {
            applet.createGraphics( applet.width, applet.height)
        }
    }

    override fun handleKeyEvent(event: KeyEvent?) {
        children.forEach { c -> c.handleKeyEvent(event) }
        view.update()
    }

    override fun handleMouseEvent(event: MouseEvent?) {
        children.forEach { c -> c.handleMouseEvent(event) }
        view.update()
    }

    override fun addChild(child: IController) {
        children.add(child)
    }

    override fun addChildren(children: Collection<IController>) {
        this.children.addAll(children)
    }
}