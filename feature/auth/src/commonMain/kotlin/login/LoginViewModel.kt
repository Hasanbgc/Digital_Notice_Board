package login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                successMessage = state.successMessage
            )
        }
    }

    fun onAction(action: LoginScreenAction){
        when(action){
            is LoginScreenAction.onLoginClick -> {
                //Authenticate the user credential and navigate to home screen
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
            is LoginScreenAction.onSignInClick -> {
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
            is LoginScreenAction.onForgotPinClick -> {}
        }
    }
}