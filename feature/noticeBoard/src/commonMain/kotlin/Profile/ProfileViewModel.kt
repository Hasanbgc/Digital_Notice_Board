package Profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasan.dnb.auth.AuthRepository
import domain.Repository
import domain.onError
import domain.onSuccess
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.UiEvent
import presentation.UiText

class ProfileViewModel(
    private val repository: Repository,
    private val authRepository: AuthRepository
): ViewModel(){
    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getProfile()
    }


    fun onAction(action: ProfileScreenAction){
        when(action){
            is ProfileScreenAction.OnLogoutClicked ->{
                openDialog()
            }
            is ProfileScreenAction.OnLogoutConfirmed ->{
                viewModelScope.launch {
                    authRepository.signOutFromGoogle()
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
    private fun getProfile(){
        viewModelScope.launch {
            repository.getProfile()
                .onSuccess { data ->
                    _state.update {
                        it.copy(
                            user = User(
                                name = data.name,
                                imageUrl = data.avatar,
                                email = data.email,
                                contactNumber = data.contactNumber
                            )
                        )
                    }
                }
                .onError {
                    _eventFlow.emit(UiEvent.ShowSnackbar(UiText.DynamicString(it.message)))
                }
        }
    }
}