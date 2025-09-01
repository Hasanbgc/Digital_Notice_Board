package com.hasan.dnb.app

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination {
    @Serializable
    data object Splash : AppDestination()
    @Serializable
    data object Auth : AppDestination()
    @Serializable
    data object Registration : AppDestination()
    @Serializable
    data object Main : AppDestination()
}
@Serializable
sealed class MainDestination {
    @Serializable
    data object Home : MainDestination()
    @Serializable
    data object Profile : MainDestination()
    @Serializable
    data object Settings : MainDestination()
}

