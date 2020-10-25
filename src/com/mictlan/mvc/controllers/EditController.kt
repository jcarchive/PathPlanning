package com.mictlan.mvc.controllers

import com.mictlan.math.geometry.IVector
import com.mictlan.math.geometry.Vector
import com.mictlan.mvc.models.EditManager
import com.mictlan.mvc.views.EditView
import processing.event.KeyEvent
import processing.event.MouseEvent

class EditController( model: EditManager, view: EditView, guid: String): Controller<EditManager>(model, view, guid) {

    override fun handleMouseEvent(event: MouseEvent?) {
        when(event?.action) {
            MouseEvent.PRESS -> handleMouseclick(event)
        }
        super.handleMouseEvent(event)
    }

    override fun handleKeyEvent(event: KeyEvent?) {
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