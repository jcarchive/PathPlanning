package com.mictlan.mvc.views

import com.mictlan.mvc.Frame
import processing.core.PApplet
import processing.core.PGraphics
import java.util.*

abstract class View<T>(protected val model: T, override val guid: String = UUID.randomUUID().toString(), var parent: IView? = null): IView {
    protected val context: Frame = Frame.instance
    protected val children: MutableCollection<IView> = mutableListOf()
    override val canvas: PGraphics = Frame.instance.createGraphics(Frame.instance.width, Frame.instance.height)

    override fun addChild( child:IView){
        children.add(child)
    }

    override fun addChildren( children: Collection<IView>){
        this.children.addAll(children)
    }

    override fun update(){
        canvas.beginDraw()
        canvas.push()
        draw(canvas)
        canvas.pop()

        for (child in children) {
            canvas.imageMode(PApplet.CORNER)
            canvas.image(child.canvas, 0f, 0f)
        }
        canvas.endDraw()

        parent?.update()
    }
}