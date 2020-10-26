package com.mictlan.mvc.controllers

import com.mictlan.math.geometry.IVector
import com.mictlan.math.geometry.Vector
import com.mictlan.mvc.models.Map
import com.mictlan.mvc.views.MapView
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent

class MapController(model: Map, view: MapView, guid: String): Controller<Map>(model, view, guid) {
    override fun handleMouseEvent(event: MouseEvent?) {
        when(event?.action) {
            MouseEvent.PRESS -> handleMouseclick(event)
        }
        super.handleMouseEvent(event)
    }

    override fun handleKeyEvent(event: KeyEvent?) {
        if(event?.action == KeyEvent.PRESS){
            when(event.key){
                '\n' -> model.pushBuffer()
                in '0'..'9' -> view.toggleLayer(event.key.toInt())
                'g'-> {
                    model.setGoal(Vector(context.mouseX.toDouble(), context.mouseY.toDouble()))?: println("Edit: Goal can't be set")
                }

                's'-> {
                    model.setStart(Vector(context.mouseX.toDouble(), context.mouseY.toDouble()))?: println("Edit: Start can't be set")
                }
                'f'-> {
                    if(model.start == null || model.goal == null)
                        println("Edit: Can't find path without start and/or goal")
                    if(model.findPath())
                        println("Edit: Path found")
                    else
                        println("Edit: Path not found")

                }
                PApplet.BACKSPACE -> if(model.buffer.count() > 0) model.buffer.remove(model.buffer.last())
            }
            if(model.buffer.count() > 0)
                when(event.keyCode){
                    PApplet.UP -> model.buffer.last().y -= 10
                    PApplet.DOWN-> model.buffer.last().y += 10
                    PApplet.LEFT -> model.buffer.last().x -= 10
                    PApplet.RIGHT -> model.buffer.last().x += 10
                }
        }
        super.handleKeyEvent(event)
    }

    private fun handleMouseclick(event: MouseEvent)
    {
        if(!event.isControlDown) return;
        val mousePosition: IVector = Vector(event.x.toDouble(), event.y.toDouble())

        model.buffer.add(mousePosition)
        println("Edit: Added $mousePosition to buffer")

    }
}