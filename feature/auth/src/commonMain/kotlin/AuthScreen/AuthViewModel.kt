package AuthScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.AppDestination
import presentation.UiEvent

class AuthViewModel: ViewModel() {
    val _authScreenState = MutableStateFlow<AuthScreenState>(AuthScreenState())
    val authScreenState = _authScreenState.asStateFlow()

    val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    var uid = "12345678"

    fun onAction(action: AuthScreenAction){
        when(action){
            is AuthScreenAction.OnGoogleClick -> {
                // for temporary test purpose
                viewModelScope.launch {
                    _authScreenState.update {
                        it.copy(
                            isGoogleLoading = true
                        )
                    }
                    delay(2000)
                    _authScreenState.update {
                        it.copy(
                            isGoogleLoading = false
                        )
                    }
                    _events.emit(UiEvent.Navigate)
                }

            }
            is AuthScreenAction.OnFacebookClick -> {

            }
            is AuthScreenAction.OnTermsClick -> {

            }
            is AuthScreenAction.OnPrivacyClick -> {

            }
        }
    }
}