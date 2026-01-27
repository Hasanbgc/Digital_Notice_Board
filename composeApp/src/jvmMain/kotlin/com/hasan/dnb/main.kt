package com.hasan.dnb

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.hasan.dnb.app.AppNavigation
import com.hasan.dnb.di.viewModelModule
import org.koin.core.context.startKoin

fun main() = application {

    startKoin {
        modules(
            platformModule,
            sharedModule,
            viewModelModule
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Digita_Notice_Board",
    ) {
        AppNavigation()
    }
}