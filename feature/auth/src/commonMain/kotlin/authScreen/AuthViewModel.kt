package authScreen

import GoogleAuthUiProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasan.dnb.auth.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.AppDestination
import presentation.UiEvent

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val _authScreenState = MutableStateFlow<AuthScreenState>(AuthScreenState())
    val authScreenState = _authScreenState.asStateFlow()

    val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    var uid = ""

    init {
        checkUserSession()
    }

    fun onAction(action: AuthScreenAction) {
        when (action) {
            is AuthScreenAction.OnGoogleClick -> {
                signInWithGoogle(action.uiProvider)
            }

            is AuthScreenAction.OnFacebookClick -> {}
            is AuthScreenAction.OnTermsClick -> {}
            is AuthScreenAction.OnPrivacyClick -> {}
        }
    }

    private fun signInWithGoogle(uiProvider: GoogleAuthUiProvider) {
        viewModelScope.launch {
            _authScreenState.update { it.copy(isGoogleLoading = true) }

            val account = uiProvider.signIn()
            println("google_account = $account")

            if (account == null) {
                _authScreenState.update {
                    it.copy(isGoogleLoading = false, errorMessage = "Sign-in cancelled.")
                }
                return@launch
            }

            authRepository.signInWithGoogle(account.idToken)
                .collect { result ->
                    result.fold(
                        onSuccess = { googleAccount ->
                            uid = googleAccount.userId
                            _authScreenState.update {
                                it.copy(isGoogleLoading = false, errorMessage = null)
                            }
                            _events.emit(UiEvent.Navigate)
                        },
                        onFailure = { e ->
                            _authScreenState.update {
                                it.copy(
                                    isGoogleLoading = false,
                                    errorMessage = e.message ?: "Sign-in failed. Please try again."
                                )
                            }
                        }
                    )
                }
        }
    }

    fun checkUserSession() {
        val uid = authRepository.checkUserSession()

        _authScreenState.update {
            it.copy(
                authLoading = false,
                uid = uid
            )
        }
    }
}
