package com.mictlan.mvc

import com.mictlan.mvc.utils.Color

class GlobalSettings {
    companion object{
        val TextColor: Color = Color( 27f,27f,27f)
        val BackgroundColor: Color = Color(225f,225f,225f)
        val ObstaclesColor:  Color = Color( 20f, 20f, 20f)
        val PrimaryColor: Color = Color(0f,0f,0f)
        val SecondaryColor: Color = Color(0f,0f,0f)
        val AlternativeColorA : Color = Color(0f,0f,0f)
        val AlternativeColorB : Color = Color(0f,0f,0f)
        val AlternativeColorC : Color = Color(0f,0f,0f)
        const val MaximumSearchCount : Int = 10000
    }
}