package login


data class LoginScreenState (
    val selectedTab:AuthTab = AuthTab.NORMAL_USER,
    val mobileNumber:String = "",
    val regMobileNumber: String = "",
    val password:String = ""
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
