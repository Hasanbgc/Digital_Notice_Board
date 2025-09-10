package registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            is RegistrationScreenAction.NormalUser.onSendCodeClick -> {
                updateRegistrationState {
                    copy(
                        isLoading = true
                    )
                }
                viewModelScope.launch {
                    delay(2000)
                    val trimmedNumber = registrationScreenState.value.mobileNumber.trimStart('0').ifEmpty { "" }
                    updateRegistrationState{
                        copy(
                            isLoading = false,
                            progressState = 1,
                            mobileNumber = trimmedNumber
                        )
                    }
                }
            }
            is RegistrationScreenAction.NormalUser.onVerifyClick -> {}
            is RegistrationScreenAction.NormalUser.onResendClick -> {}
            is RegistrationScreenAction.NormalUser.onGoBackClick -> {
                updateRegistrationState {
                    copy(
                        progressState = 0
                    )
                }
            }
            is RegistrationScreenAction.NormalUser.onFindLocationClick -> {}
            is RegistrationScreenAction.NormalUser.onSkipClick -> {}
            is RegistrationScreenAction.NormalUser.onCompleteClick -> {}
            is RegistrationScreenAction.NormalUser.onOTPEntered ->{
                updateRegistrationState {
                    copy(
                        otpCode = action.otp,
                        verifyButtonEnabled = true
                    )
                }
            }

            is RegistrationScreenAction.NoticePoster.InstituteDropDownClick -> {}
            is RegistrationScreenAction.NoticePoster.InstituteTypeSelect -> {}
            is RegistrationScreenAction.NoticePoster.NIDVerification -> {}
            is RegistrationScreenAction.NoticePoster.OnCompleteClick -> {}
        }
    }
}