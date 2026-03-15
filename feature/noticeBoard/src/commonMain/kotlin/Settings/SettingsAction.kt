package Settings

sealed class SettingsEvent {

    object EditProfileClicked : SettingsEvent()
    object ChangePinClicked : SettingsEvent()
    object LocationClicked : SettingsEvent()

    data class ToggleSms(val enabled: Boolean) : SettingsEvent()
    data class ToggleVoice(val enabled: Boolean) : SettingsEvent()
    data class TogglePush(val enabled: Boolean) : SettingsEvent()

    data class ToggleProfileVisibility(val enabled: Boolean) : SettingsEvent()

    object PrivacyPolicyClicked : SettingsEvent()
    object TermsClicked : SettingsEvent()

    object HelpCenterClicked : SettingsEvent()
    object ContactSupportClicked : SettingsEvent()

    data class LanguageSelected(val language: Language) : SettingsEvent()

    object LogoutClicked : SettingsEvent()
    object  InterestCategoriesClicked: SettingsEvent()

}