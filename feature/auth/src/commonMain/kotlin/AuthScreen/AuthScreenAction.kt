package AuthScreen

sealed class AuthScreenAction{
    object OnGoogleClick: AuthScreenAction()
    object OnFacebookClick: AuthScreenAction()
    object OnTermsClick: AuthScreenAction()
    object OnPrivacyClick: AuthScreenAction()
}