package com.mictlan.mvc.controllers

import com.mictlan.mvc.views.IView
import processing.event.KeyEvent
import processing.event.MouseEvent

interface IController {

    fun handleKeyEvent( event: KeyEvent?);
    fun handleMouseEvent( event: MouseEvent?);
}