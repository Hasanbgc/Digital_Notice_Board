package login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.AuthTab
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val _loginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    fun updateLoginState(state:LoginScreenState){
        _loginScreenState.update {
            it.copy(
                selectedTab = state.selectedTab,
                mobileNumber = state.mobileNumber,
                regMobileNumber = state.regMobileNumber,
                password = state.password,
                loading = state.loading,
                errorMessage = state.errorMessage,
                successMessage = state.successMessage,
                otpTimerEnabled = state.otpTimerEnabled,
                screenState = state.screenState,
                otpResendEnabled = state.otpResendEnabled,
                otpTimer = state.otpTimer,
                otp = state.otp
            )
        }
    }

    fun onAction(action: LoginScreenAction){
        when(action){
            is LoginScreenAction.OnLoginClick -> {
                //Authenticate the user credential and navigate to home screen
                viewModelScope.launch {
                    _loginScreenState.update {
                        it.copy(
                            loading = true
                        )
                    }
                    delay(1000)
                    _loginScreenState.update {
                        it.copy(
                            loading = false,
                            screenState = 1
                        )
                    }
                }
            }
            is LoginScreenAction.OnSignInClick -> {
                //Navigate to sign in screen
                viewModelScope.launch {
                    _loginScreenState.update {
                        it.copy(
                            loading = true
                        )
                    }
                    delay(2000)
                    _loginScreenState.update {
                        it.copy(
                            loading = false,
                            successMessage = "Login Success"
                        )
                    }

                }
            }
            is LoginScreenAction.OnForgotPinClick -> {}
            is LoginScreenAction.BackFromOTPVerification -> {
                viewModelScope.launch {
                    _loginScreenState.update {
                        it.copy(
                            screenState = 0
                        )
                    }
                }
            }

            LoginScreenAction.OnResendOtpClick -> {
                viewModelScope.launch {
                    _loginScreenState.update {
                        it.copy(
                            otpTimerEnabled = true
                        )
                    }
                }
            }
            LoginScreenAction.OnVerifyOtpClick -> {
                viewModelScope.launch {
                    _loginScreenState.update {
                        it.copy(
                            loading = true
                        )
                    }
                    delay(1000)
                    _loginScreenState.update {
                        it.copy(
                            loading = false,
                            successMessage = "Login Success"
                        )
                    }
                }
            }
        }
    }
}