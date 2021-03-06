package com.mictlan.mvc.controllers

import com.mictlan.math.geometry.IVector
import com.mictlan.math.geometry.Vector
import com.mictlan.mvc.models.Map
import com.mictlan.mvc.views.MapView
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent
import java.lang.Exception

class MapController(model: Map, view: MapView, guid: String): Controller<Map>(model, view, guid) {
    override fun handleMouseEvent(event: MouseEvent?) {
        when(event?.action) {
            MouseEvent.PRESS -> handleMouseclick(event)
            MouseEvent.MOVE -> {
                if(event.isAltDown){
                    val mousePosition = Vector(event.x.toDouble(), event.y.toDouble())
                        try
                        {
                            model.setGoal(mousePosition)
                            model.findPath()
                            model.pathSmoothing()
                            view.update()
                        } catch (e: Exception) {

                        }
                }
            }
        }
    }

    override fun handleKeyEvent(event: KeyEvent?) {
        if(event?.action == KeyEvent.PRESS){
            when(event.key){
                '\n' -> model.pushBuffer()
                in '0'..'9' -> view.toggleLayer(event.key.toInt())
                'l'->{

                    model.setGoal(Vector(context.mouseX.toDouble(), context.mouseY.toDouble()))?: println("Edit: Goal can't be set")
                    if(model.findPath())
                        model.pathSmoothing()
                    else
                        println("Edit: Path not found")
                }
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
                ' ' -> model.pathSmoothing()
                'x' -> model.splitPath()
                'r' -> {model.splitPath(); model.pathSmoothing()}
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
        if(event.isAltDown){
            if(event.action == MouseEvent.DRAG){
                model.setGoal(Vector(context.mouseX.toDouble(), context.mouseY.toDouble()))?: println("Edit: Goal can't be set")
                if(model.findPath()) {
                    model.pathSmoothing()
                    for(j in 1..5) {
                        model.splitPath()
                        model.splitPath()
                        model.splitPath()
                        model.splitPath()
                        model.pathSmoothing()
                    }
                }
                else
                    println("Edit: Path not found")
            }
        }
        if(!event.isControlDown) return;
        val mousePosition: IVector = Vector(event.x.toDouble(), event.y.toDouble())

        model.buffer.add(mousePosition)
        println("Edit: Added $mousePosition to buffer")
        view.update()

    }
}