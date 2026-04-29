package Settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel: ViewModel() {


    val _uiState = MutableStateFlow(SettingsState())
    val state = _uiState.asStateFlow()

    fun OnAction(event: SettingsAction){
        when(event){
            is SettingsAction.EditProfileClicked -> {

            }
            SettingsAction.ChangePinClicked -> {}
            SettingsAction.ContactSupportClicked -> {}
            SettingsAction.HelpCenterClicked -> {}
            is SettingsAction.LanguageSelected -> {}
            SettingsAction.LocationClicked -> {}
            SettingsAction.LogoutClicked -> {}
            SettingsAction.PrivacyPolicyClicked -> {}
            SettingsAction.TermsClicked -> {}
            is SettingsAction.ToggleProfileVisibility -> {}
            is SettingsAction.TogglePush -> {}
            is SettingsAction.ToggleSms -> {}
            is SettingsAction.ToggleVoice -> {}
            is SettingsAction.InterestCategoriesClicked -> {}
        }
    }

    var isDarkMode = false

}