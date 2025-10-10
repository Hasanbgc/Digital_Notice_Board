package registration

sealed interface RegistrationScreenAction {
    sealed interface NormalUser : RegistrationScreenAction {
        object onSendCodeClick : RegistrationScreenAction
        object onVerifyClick : RegistrationScreenAction
        object onResendClick : RegistrationScreenAction
        object onGoBackClick : RegistrationScreenAction
        object onFindLocationClick : RegistrationScreenAction
        object onSkipClick : RegistrationScreenAction
        object onCompleteClick : RegistrationScreenAction
        data class onOTPEntered(val otp: String): RegistrationScreenAction
    }
    sealed interface NoticePoster : RegistrationScreenAction {
        object InstituteDropDownClick : NoticePoster
        data class InstituteTypeSelect(val type: String) : NoticePoster
        object InstitutionSelect : NoticePoster
        object NIDVerification : NoticePoster
        object OnCompleteClick : NoticePoster
    }
}