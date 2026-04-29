package presentation


import androidx.navigation3.runtime.NavKey
import com.hasan.dnb.domain.UserSession
import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination : NavKey {
    @Serializable
    data object Auth : AppDestination()

    @Serializable
    data object Registration : AppDestination()

    @Serializable
    data class Main(val userSession: UserSession) : AppDestination()
}

@Serializable
sealed class MainDestination() : NavKey {
    @Serializable
    data object Home : MainDestination()

    @Serializable
    data object Profile : MainDestination()

    @Serializable
    data object Settings : MainDestination()

    @Serializable
    data object CreateNotice : MainDestination()
}

@Serializable
sealed class HomeDestination {
    @Serializable
    data object ForYou : HomeDestination()
    @Serializable
    data object NearBy : HomeDestination()
    @Serializable
    data object Saved : HomeDestination()
}


