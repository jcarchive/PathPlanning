package com.mictlan.mvc

import com.mictlan.math.geometry.Vector
import com.mictlan.mvc.controllers.CursorController
import com.mictlan.mvc.controllers.MapController
import com.mictlan.mvc.controllers.IController
import com.mictlan.mvc.controllers.KeyLoggerController
import com.mictlan.mvc.models.Cursor
import com.mictlan.mvc.models.Map
import com.mictlan.mvc.models.KeyLogger
import com.mictlan.mvc.utils.background
import com.mictlan.mvc.views.CursorView
import com.mictlan.mvc.views.MapView
import com.mictlan.mvc.views.IView
import com.mictlan.mvc.views.KeyLoggerView
import processing.core.PApplet
import processing.event.KeyEvent
import processing.event.MouseEvent

class Frame: PApplet() {
    private val controllers: MutableCollection<IController> = mutableListOf()
    private val views: MutableCollection<IView> = mutableListOf()

    companion object Factory{
        val instance: Frame = Frame()

        fun run(){
            instance.runSketch()
        }
    }

    override fun settings() {
        size(800,500)
    }

    override fun setup() {
        frameRate(30f)
        noCursor();

        val editManager = Map()
        editManager.buffer.add(Vector())
        editManager.buffer.add(Vector(width.toDouble()))
        editManager.buffer.add(Vector(width.toDouble(), (height - 40).toDouble()))
        editManager.buffer.add(Vector( y = (height - 40).toDouble()))
        editManager.pushBuffer()

        val editView = MapView(editManager, "EditView")
        val editController = MapController(editManager, editView, "EditController")
        controllers.add(editController)
        views.add(editView)

        val keyLogger = KeyLogger()
        val keyLoggerView = KeyLoggerView(keyLogger,"KeyLoggerView")
        val keyController = KeyLoggerController(keyLogger, keyLoggerView, "KeyLoggerController")
        controllers.add(keyController)
        views.add(keyLoggerView)

        val cursor = Cursor(Vector())
        val cursorView= CursorView(cursor,"CursorView")
        val cursorController = CursorController(cursor, cursorView, "CursorController")
        controllers.add(cursorController)
        views.add(cursorView)

        for (view in views) {
            view.update()
        }
    }

    override fun draw() {
        background(GlobalSettings.BackgroundColor)
        for (view in views) {
            image(view.canvas, 0f, 0f)
        }
    }

    override fun handleKeyEvent(event: KeyEvent?) {
        super.handleKeyEvent(event)
        for (iController in controllers) {
            iController.handleKeyEvent(event)
        }

    }

    override fun handleMouseEvent(event: MouseEvent?) {
        super.handleMouseEvent(event)
        for (iController in controllers) {
            iController.handleMouseEvent(event)
        }
    }
}