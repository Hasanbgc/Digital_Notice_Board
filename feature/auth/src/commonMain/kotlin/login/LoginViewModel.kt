package login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {

    val _tabSelected = MutableStateFlow<AuthTab>(AuthTab.NORMAL_USER)
    val tabSelected = _tabSelected.asStateFlow()

    fun setTab(tab: AuthTab){
        _tabSelected.value = tab
    }
}