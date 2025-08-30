package login

sealed interface LoginScreenAction {
    object onLoginClick: LoginScreenAction
    object onSignInClick: LoginScreenAction
    object onForgotPinClick: LoginScreenAction
}