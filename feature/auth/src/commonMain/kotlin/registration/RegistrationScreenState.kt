package registration

import common.AuthTab
data class RegistrationScreenState(
    val selectedTab: AuthTab = AuthTab.NORMAL_USER,
    val isLoading: Boolean = false,

    // Common fields
    val mobileNumber: String = "",
    val progressState: Int = 1, //0 - phone verification, 1 - otp verification, 2 - complete profile

    // Normal User specific
    val otpCode: String = "",
    val location: String = "",
    val verifyButtonEnabled: Boolean = false,

    // Notice Poster specific
    val name: String = "",
    val email: String = "",
    val institute: String = "",
    val instituteType: String = "",
    val nid: String = "",
    val noticePosterLoading: Boolean = false, // Separate loading for notice poster if needed
)