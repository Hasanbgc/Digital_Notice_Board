package login

import registration.RegistrationScreenAction

sealed interface LoginScreenAction {
    object OnLoginClick: LoginScreenAction
    object OnSignInClick: LoginScreenAction
    object OnForgotPinClick: LoginScreenAction

    object BackFromOTPVerification : LoginScreenAction
    object OnResendOtpClick : LoginScreenAction
    object OnVerifyOtpClick : LoginScreenAction
}