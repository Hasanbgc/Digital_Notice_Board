package login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel: ViewModel() {
    val _loginScreenState = MutableStateFlow<LoginScreenState>(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    fun updateLoginState(state:LoginScreenState){
        _loginScreenState.update {
            it.copy(
                selectedTab = state.selectedTab,
                mobileNumber = state.mobileNumber,
                regMobileNumber = state.regMobileNumber,
                password = state.password
            )
        }
    }
}