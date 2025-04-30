package com.example.noteapp_uygulamasi.ui.theme

import androidx.compose.ui.graphics.Color


sealed class ThemeColors(
    val primary:Color,
    val background:Color,
    val surface:Color,
    val text:Color
){
    object Night: ThemeColors(
        primary = Color(0xFF9BA485),
        surface = Color(0xFF212A3E),
        background = Color(0xFF394867),
        text = Color(0xFFF1F6F9)
    )
    object Day: ThemeColors(
        primary = Color(0xFFBED1CF),
        surface = Color(0xFFFFE4C9),
        background = Color(0xFFFFF7F1),
        text = Color(0xFF000000)
    )

}