package com.mictlan.mvc.views

import com.mictlan.mvc.Frame
import com.mictlan.mvc.models.KeyLogger
import processing.core.PGraphics

class KeyLoggerView(model: KeyLogger, override val guid: String) : View<KeyLogger>(model, guid) {

    override fun draw(canvas: PGraphics) {
        canvas.clear()
        canvas.fill(255)
        canvas.text(model.toString() ,  10f, canvas.height - 10f)
    }
}