package registration

import common.AuthTab
data class RegistrationScreenState(
    val selectedTab: AuthTab = AuthTab.NOTICE_POSTER,
    val isLoading: Boolean = false,

    // Common fields
    val mobileNumber: String = "",
    val progressState: Int = 0, //0 - phone verification, 1 - otp verification, 2 - complete profile

    // Normal User specific
    val otpCode: String = "",
    val location: String = "",
    val verifyButtonEnabled: Boolean = false,
    val otpError: Boolean = false,
    var detectLocation: Int = 0,
    val latitude: Double = 23.777176, // Default to Dhaka latitude
    val longitude: Double = 90.399452, // Default to Dhaka longitude
    val navigateToLogin: Boolean = false,
    val navigateToMain: Boolean = false,


    // Notice Poster specific
    val name: String = "",
    val email: String = "",
    val institute: String = "",
    val instituteType: String = "",
    val instituteDropdownClicked: Boolean = false,
    val nid: String = "",
    val noticePosterLoading: Boolean = false,
    val moreClicked: Boolean = false, // Separate loading for notice poster if needed
)