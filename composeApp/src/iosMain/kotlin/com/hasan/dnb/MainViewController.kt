package com.hasan.dnb

import androidx.compose.ui.window.ComposeUIViewController
import com.hasan.dnb.app.AppNavigation

fun MainViewController() = ComposeUIViewController {
    initKoin()
    AppNavigation()
}