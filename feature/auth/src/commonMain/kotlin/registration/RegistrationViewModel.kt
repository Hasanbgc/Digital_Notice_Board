package registration

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import registration.component.InstitutionTypeUi

class RegistrationViewModel : ViewModel() {
    private val _registrationScreenState = MutableStateFlow(RegistrationScreenState())
    val registrationScreenState: StateFlow<RegistrationScreenState> =
        _registrationScreenState.asStateFlow()

    fun updateRegistrationState(updateState: RegistrationScreenState.() -> RegistrationScreenState) {
        _registrationScreenState.update { it.updateState() }
    }

    // Usage:
    /*viewModel.updateRegistrationState {
        copy(mobileNumber = "123456789", isLoading = true)
    }*/
    fun onAction(action: RegistrationScreenAction) {
        when (action) {
            is RegistrationScreenAction.NormalUser.onSendCodeClick -> {
                updateRegistrationState {
                    copy(
                        isLoading = true
                    )
                }
                viewModelScope.launch {
                    delay(2000)
                    val trimmedNumber =
                        registrationScreenState.value.mobileNumber.trimStart('0').ifEmpty { "" }
                    updateRegistrationState {
                        copy(
                            isLoading = false,
                            progressState = 1,
                            mobileNumber = trimmedNumber
                        )
                    }
                }
            }

            is RegistrationScreenAction.NormalUser.onVerifyClick -> {
                //check verification code by calling api
                val isError = registrationScreenState.value.otpCode != "1234"
                if (isError) {
                    updateRegistrationState {
                        copy(
                            otpError = isError
                        )
                    }
                } else {
                    updateRegistrationState {
                        copy(
                            progressState = 2
                        )
                    }
                }
            }

            is RegistrationScreenAction.NormalUser.onResendClick -> {}
            is RegistrationScreenAction.NormalUser.onGoBackClick -> {
                updateRegistrationState {
                    copy(
                        progressState = 0
                    )
                }
            }

            is RegistrationScreenAction.NormalUser.onFindLocationClick -> {
                updateRegistrationState {
                    copy(
                        detectLocation = detectLocation+1
                    )
                }

            }
            is RegistrationScreenAction.NormalUser.onSkipClick -> {
                updateRegistrationState {
                    copy(
                        progressState = 2
                    )
                }
            }
            is RegistrationScreenAction.NormalUser.onCompleteClick -> {
                // Here you would typically send the registration data including location to your backend
                val state = registrationScreenState.value
                println("Completing registration with location: ${state.latitude}, ${state.longitude}")
                updateRegistrationState {
                    copy(
                        isLoading = true
                    )
                }
                // Simulate API call
                viewModelScope.launch {
                    delay(2000)
                    updateRegistrationState {
                        copy(
                            isLoading = false,
                            //navigateToLogin = true,
                            navigateToMain = true
                            // You might want to navigate to a success screen here
                        )
                    }
                }
            }
            is RegistrationScreenAction.NormalUser.onOTPEntered -> {
                println("OTP_Entered: ${action.otp}")
                if (action.otp.length == 4) {
                    updateRegistrationState {
                        copy(
                            otpCode = action.otp,
                            verifyButtonEnabled = true
                        )
                    }
                } else {
                    updateRegistrationState {
                        copy(
                            otpCode = action.otp,
                            verifyButtonEnabled = false
                        )
                    }
                }
            }

            is RegistrationScreenAction.NoticePoster.InstituteDropDownClick -> {}
            is RegistrationScreenAction.NoticePoster.InstituteTypeSelect -> {
                updateRegistrationState {
                    copy(
                        instituteType = action.type
                        //filterInstitution(action.type)
                    )
                }
            }
            is RegistrationScreenAction.NoticePoster.InstitutionSelect -> {}
            is RegistrationScreenAction.NoticePoster.NIDVerification -> {}
            is RegistrationScreenAction.NoticePoster.OnCompleteClick -> {}
        }
    }

    fun institutionTypes():List<InstitutionTypeUi>{
        return listOf(
            InstitutionTypeUi(
                label = "School",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Madrasha",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "college",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "University",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Polytechnic institute",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Kindergarten",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Mosque",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Hospital",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Clinic",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Union Office",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Upazila Parishad",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Municipality Office",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Police Station",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Fire Station",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Supreme Court",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "High Court",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "District Court",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Magistrate Court",
                icon = Icons.Default.School
            ),
            InstitutionTypeUi(
                label = "Blood Donation Center",
                icon = Icons.Default.School
            ),

        )

    }
}