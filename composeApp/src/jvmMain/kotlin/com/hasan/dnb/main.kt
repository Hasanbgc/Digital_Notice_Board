package com.hasan.dnb

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hasan.dnb.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Digita_Notice_Board",
    ) {
        App()
    }
}