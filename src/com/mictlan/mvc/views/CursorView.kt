package com.mictlan.mvc.views

import com.mictlan.mvc.models.Cursor
import processing.core.PGraphics

class CursorView(model: Cursor, guid: String): View<Cursor>(model, guid){
    override fun draw(canvas: PGraphics) {
        canvas.clear()
        canvas.noStroke()
        canvas.fill(255)
        canvas.ellipse(model.position.x.toFloat(), model.position.y.toFloat(), 10f,10f)
    }
}