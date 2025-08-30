package com.hasan.dnb.app

import Constant
import Constant.Companion
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hasan.dnb.splashScreen.SplashScreenRoot
import com.hasan.dnb.splashScreen.SplashViewModel
import home.HomeScreenRoot
import home.HomeViewModel
import login.LoginScreenRoot
import login.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    Scaffold { innerPadding ->
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppDestination.Auth,
        ) {
            composable<AppDestination.Splash> {
                val viewModel: SplashViewModel = viewModel()
                SplashScreenRoot(viewModel) { destination ->
                    when (destination) {
                        Constant.MAIN -> navController.navigate(AppDestination.Main) {
                            popUpTo(AppDestination.Splash) { inclusive = true }
                        }

                        Constant.AUTH -> navController.navigate(AppDestination.Auth) {
                            popUpTo(AppDestination.Splash) { inclusive = true }
                        }

                        else -> {}
                    }
                }
            }
            composable<AppDestination.Auth> {
                val viewModel: LoginViewModel = viewModel()
                LoginScreenRoot(innerPadding,viewModel) {
                    navController.navigate(AppDestination.Main,){
                        popUpTo(AppDestination.Auth) { inclusive = true }
                    }
                }
            }

            composable<AppDestination.Main> {
                HomeScreen()
            }
        }
    }
}