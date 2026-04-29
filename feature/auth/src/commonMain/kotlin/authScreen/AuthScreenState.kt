package authScreen

import presentation.AppDestination

data class AuthScreenState(
    val isGoogleLoading: Boolean = false,
    val isFacebookLoading: Boolean = false,
    val errorMessage: String? = null,
    val uid: String? = null,
    val authLoading: Boolean = true,
)
