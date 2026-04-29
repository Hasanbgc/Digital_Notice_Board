package Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasan.dnb.auth.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.UiEvent

class ProfileViewModel(private val repository: AuthRepository): ViewModel(){
    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()




    fun onAction(action: ProfileScreenAction){
        when(action){
            is ProfileScreenAction.OnLogoutClicked ->{
                openDialog()
            }
            is ProfileScreenAction.OnLogoutConfirmed ->{
                viewModelScope.launch {
                    repository.signOutFromGoogle()
                }
                closeDialog()
            }
            is ProfileScreenAction.OnLogoutCancelled ->{
                closeDialog()
            }

            else -> {}

        }
    }

    private fun openDialog(){
        _state.update {
            it.copy(
                showLogOutDialog = true
            )
        }
    }

    private fun closeDialog(){
        _state.update {
            it.copy(
                showLogOutDialog = false
            )
        }
    }

}