package com.hasan.dnb.splashScreen

sealed interface SplashScreenAction{
    object Loading: SplashScreenAction
    object Complete: SplashScreenAction
}
