package login

import common.AuthTab


data class LoginScreenState (
    val selectedTab: AuthTab = AuthTab.NORMAL_USER,
    val mobileNumber:String = "",
    val regMobileNumber: String = "",
    val password:String = "",
    val loading:Boolean = false,
    val errorMessage:String? = null,
    val successMessage:String? = null,
    val screenState: Int = 0, //0 - phone verification, 1 - otp verification, 2 - complete profile


    // Number Verification
    val otp:String = "",
    val otpTimer:String = "",
    val otpTimerEnabled:Boolean = false,
    val otpResendEnabled:Boolean = false,
)
/*sealed class LoginScreenState {
    data class normalUserState(
        val mobileNumber: String = "",
    ):LoginScreenState()
    data class noticePosterState(
        val mobileNumber: String = "",
        val password: String = ""
    ): LoginScreenState()
}*/
