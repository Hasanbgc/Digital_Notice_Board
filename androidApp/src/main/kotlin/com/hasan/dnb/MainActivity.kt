package com.hasan.dnb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.hasan.dnb.app.AppNavigation
import com.hasan.dnb.app.AppViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import presentation.AppDestination

class MainActivity : ComponentActivity() {
    val viewModel: AppViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        splash.apply {
            setKeepOnScreenCondition {
                viewModel.destination.value == null
            }
        }


        setContent {
            val dest = viewModel.destination.collectAsState().value
            when (dest) {
                is AppDestination.Main -> {
                    AppNavigation(dest)
                }
                is AppDestination.Auth -> {
                    AppNavigation(dest)
                }
                else -> {}
            }
        }
    }
}
