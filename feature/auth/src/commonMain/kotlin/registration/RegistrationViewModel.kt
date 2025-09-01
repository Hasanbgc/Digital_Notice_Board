package registration

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel: ViewModel() {
    private val _registrationScreenState = MutableStateFlow(RegistrationScreenState())
    val registrationScreenState: StateFlow<RegistrationScreenState> = _registrationScreenState.asStateFlow()

    fun updateRegistrationState(updateState: RegistrationScreenState.() -> RegistrationScreenState){
        _registrationScreenState.update {it.updateState()}
    }

    // Usage:
    /*viewModel.updateRegistrationState {
        copy(mobileNumber = "123456789", isLoading = true)
    }*/
    fun onAction(action: RegistrationScreenAction){
        when(action){
            is RegistrationScreenAction.NormalUser.onSendCodeClick -> {}
            is RegistrationScreenAction.NormalUser.onVerifyClick -> {}
            is RegistrationScreenAction.NormalUser.onResendClick -> {}
            is RegistrationScreenAction.NormalUser.onGoBackClick -> {}
            is RegistrationScreenAction.NormalUser.onFindLocationClick -> {}
            is RegistrationScreenAction.NormalUser.onSkipClick -> {}
            is RegistrationScreenAction.NormalUser.onCompleteClick -> {}

            is RegistrationScreenAction.NoticePoster.InstituteDropDownClick -> {}
            is RegistrationScreenAction.NoticePoster.InstituteTypeSelect -> {}
            is RegistrationScreenAction.NoticePoster.NIDVerification -> {}
            is RegistrationScreenAction.NoticePoster.OnCompleteClick -> {}
        }
    }
}