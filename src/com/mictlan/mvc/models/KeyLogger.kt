package com.mictlan.mvc.models

import processing.event.KeyEvent

class KeyLogger {
    var key: Char? = null
    var action: Int? = null

    override fun toString(): String {
        val actionType = when(action){
            KeyEvent.PRESS -> "Pressed (${action})"
            KeyEvent.RELEASE -> "Released (${action})"
            KeyEvent.TYPE -> "Type (${action})"
            else -> "NoChecked (${action})"
        }

        return "Key: $key    Action: $actionType"
    }
}