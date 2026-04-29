package authScreen

import com.hasan.dnb.domain.UserSession
import presentation.AppDestination

data class AuthScreenState(
    val isGoogleLoading: Boolean = false,
    val isFacebookLoading: Boolean = false,
    val errorMessage: String? = null,
    val userSession: UserSession? = null,
    val authLoading: Boolean = true,
)
