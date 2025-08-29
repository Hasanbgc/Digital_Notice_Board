package login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {

    val _tabSelected = MutableStateFlow<AuthTab>(AuthTab.NORMAL_USER)
    val tabSelected = _tabSelected.asStateFlow()

    val _mobileNumber = MutableStateFlow<String>("")
    val mobileNumber = _mobileNumber.asStateFlow()

    val _password = MutableStateFlow<String>("")
    val password = _password.asStateFlow()

    fun setTab(tab: AuthTab){
        _tabSelected.value = tab
    }

    fun setPassword(password: String){
        _password.value = password
    }
    fun setMobileNumber(mobileNumber: String){
        _mobileNumber.value = mobileNumber
    }
}