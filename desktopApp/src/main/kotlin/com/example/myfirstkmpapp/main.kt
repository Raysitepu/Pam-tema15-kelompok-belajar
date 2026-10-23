package com.example.myfirstkmpapp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    val windowState = rememberWindowState(width = 800.dp, height = 950.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "PAM - Tema 15 Kelompok Belajar",
    ) {
        App()
    }
}
