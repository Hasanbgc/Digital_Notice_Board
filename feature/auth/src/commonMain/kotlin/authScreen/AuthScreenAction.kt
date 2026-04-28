package authScreen

import GoogleAuthUiProvider

sealed class AuthScreenAction {
    data class OnGoogleClick(val uiProvider: GoogleAuthUiProvider) : AuthScreenAction()
    object OnFacebookClick : AuthScreenAction()
    object OnTermsClick : AuthScreenAction()
    object OnPrivacyClick : AuthScreenAction()
}