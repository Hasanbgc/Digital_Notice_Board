package Settings

data class SettingsState(
    val smsEnabled: Boolean = true,
    val voiceAlertsEnabled: Boolean = false,
    val pushEnabled: Boolean = true,
    val profileVisibility: Boolean = true,
    val selectedLanguage: Language = Language.ENGLISH
)

enum class Language {
    ENGLISH,
    BANGLA
}