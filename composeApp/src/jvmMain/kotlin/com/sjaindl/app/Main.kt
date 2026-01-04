package com.sjaindl.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sjaindl.app.di.initKoin

fun main() = application {
    initKoin ()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Invest Copilot"
    ) {
        App()
    }
}
