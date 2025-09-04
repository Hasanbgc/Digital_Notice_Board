package com.hasan.dnb.app

import Constant
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hasan.dnb.splashScreen.SplashScreenRoot
import com.hasan.dnb.splashScreen.SplashViewModel
import login.LoginScreenRoot
import login.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import registration.RegistrationScreenRoot
import registration.RegistrationViewModel


@Composable
@Preview
fun App() {
    Scaffold { innerPadding ->
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = AppDestination.Registration,
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
                    navController.navigate(AppDestination.Registration){
                        popUpTo(AppDestination.Auth) { inclusive = false }
                    }

                    /*navController.navigate(AppDestination.Main,){
                        popUpTo(AppDestination.Auth) { inclusive = true }
                    }*/
                }
            }

            composable<AppDestination.Main> {
                HomeScreen()
            }

            composable<AppDestination.Registration >{
                val viewModel: RegistrationViewModel = viewModel()
                RegistrationScreenRoot(innerPadding,viewModel) {
                    navController.navigate(AppDestination.Main){
                        popUpTo(AppDestination.Registration) { inclusive = true }
                    }
                }
            }
        }
    }
}