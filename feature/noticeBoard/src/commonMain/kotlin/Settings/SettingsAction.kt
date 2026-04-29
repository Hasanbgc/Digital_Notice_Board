package Settings

sealed interface SettingsAction {

    object EditProfileClicked : SettingsAction
    object ChangePinClicked : SettingsAction
    object LocationClicked : SettingsAction

    data class ToggleSms(val enabled: Boolean) : SettingsAction
    data class ToggleVoice(val enabled: Boolean) : SettingsAction
    data class TogglePush(val enabled: Boolean) : SettingsAction

    data class ToggleProfileVisibility(val enabled: Boolean) : SettingsAction

    object PrivacyPolicyClicked : SettingsAction
    object TermsClicked : SettingsAction

    object HelpCenterClicked : SettingsAction
    object ContactSupportClicked : SettingsAction

    data class LanguageSelected(val language: Language) : SettingsAction

    object LogoutClicked : SettingsAction
    object  InterestCategoriesClicked: SettingsAction

}