package com.mictlan.mvc

import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent

class Canvas: PApplet() {
    companion object Factory{
        fun run(){
            val canvas = Canvas()
            canvas.runSketch()
        }
    }

    override fun settings() {
        size(500,500)
    }

    override fun setup() {
        frameRate(30f)

    }

    override fun draw() {
        background(0)
        fill(255)
        ellipse(mouseX.toFloat(), mouseY.toFloat(), 100.0f , 100.0f)

    }

    override fun handleKeyEvent(event: KeyEvent?) {
        super.handleKeyEvent(event)
    }

    override fun handleMouseEvent(event: MouseEvent?) {
        super.handleMouseEvent(event)
    }
}