package com.mictlan.mvc.controllers

import com.mictlan.mvc.models.KeyLogger
import com.mictlan.mvc.views.KeyLoggerView
import processing.event.KeyEvent

class KeyLoggerController(model: KeyLogger, view: KeyLoggerView, guid: String) : Controller<KeyLogger>(model, view, guid) {

    override fun handleKeyEvent(event: KeyEvent?) {
        super.handleKeyEvent(event)
        model.key = event?.key
        model.action = event?.action
        view.update()
    }
}