package home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel: ViewModel() {

    val _homeScreenState = MutableStateFlow(HomeScreenState())
    val state = _homeScreenState.asStateFlow()

    fun updateHomeState(state: HomeScreenState){
        _homeScreenState.update { it.copy(
            isLoading = state.isLoading,
            poster = state.poster,
            error = state.error
        ) }
    }
    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.OnEmergencyAlertDismiss -> {}
            is HomeScreenAction.OnEmergencyPosterClicked -> {}
            is HomeScreenAction.OnImageClicked -> {}
            is HomeScreenAction.OnLikeClicked -> {}
            is HomeScreenAction.OnShareClicked -> {}
            is HomeScreenAction.OnCommentClicked -> {}
            HomeScreenAction.OnSavedClicked -> {}
            is HomeScreenAction.OnProfileClicked -> {}
            is HomeScreenAction.OnLocationClicked -> {}
            is HomeScreenAction.PostANoticeClicked -> {}
        }
    }

}