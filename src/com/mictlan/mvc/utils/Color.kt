package com.mictlan.mvc.utils

import processing.core.PGraphics

class Color(val red: Float, val green: Float, val blue: Float, val alpha: Float= 255f) {
}

fun PGraphics.fill( color: Color){
    this.fill(color.red, color.green, color.blue, color.alpha)
}

fun PGraphics.stroke(color: Color){
    this.stroke(color.red, color.green, color.blue, color.alpha)
}

fun PGraphics.background(color: Color){
    this.background(color.red, color.green, color.blue, color.alpha)
}