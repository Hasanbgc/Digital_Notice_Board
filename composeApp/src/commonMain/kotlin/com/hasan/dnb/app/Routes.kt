package com.hasan.dnb.app


import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination : NavKey {
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

