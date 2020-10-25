package com.mictlan.mvc.controllers

import com.mictlan.math.geometry.Vector
import com.mictlan.mvc.models.Cursor
import com.mictlan.mvc.views.IView
import processing.event.KeyEvent
import processing.event.MouseEvent

class CursorController(model: Cursor, view: IView, guid: String): Controller<Cursor>(model, view, guid) {
    override fun handleKeyEvent(event: KeyEvent?) {
        super.handleKeyEvent(event)
    }

    override fun handleMouseEvent(event: MouseEvent?) {
        super.handleMouseEvent(event)
        model.position = event?.let { Vector(event.x.toDouble(), event.y.toDouble()) }!!
        view.update()
    }
}