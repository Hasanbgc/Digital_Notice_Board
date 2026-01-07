package home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel: ViewModel() {

    val _homeScreenState = MutableStateFlow(HomeScreenState())
    val state = _homeScreenState.asStateFlow()

   /* fun updateHomeState(state: HomeScreenState){
        _homeScreenState.update { it.copy(
            isLoading = state.isLoading,
            poster = state.poster,
            error = state.error
        ) }
    }*/
    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.OnEmergencyAlertDismiss -> toggleEmergencyAlert(true)
            is HomeScreenAction.OnEmergencyPosterClicked -> {}
            is HomeScreenAction.OnImageClicked -> {}
            is HomeScreenAction.OnLikeClicked -> {
                _homeScreenState.update {
                    it.copy(
                        poster = it.poster.map { poster ->
                            if (poster is Poster.Normal && poster.id == action.id) {
                                poster.copy(liked = if (action.liked)Like.LIKED else Like.UNLIKED ) // or !poster.liked to toggle
                            } else {
                                poster // Return unchanged poster
                            }
                        }
                    )
                }
            }
            is HomeScreenAction.OnShareClicked -> {}
            is HomeScreenAction.OnCommentClicked -> {}
            HomeScreenAction.OnSavedClicked -> {}
            is HomeScreenAction.OnProfileClicked -> {}
            is HomeScreenAction.OnLocationClicked -> {}
            is HomeScreenAction.PostANoticeClicked -> {}
            is HomeScreenAction.OnNotificationClicked -> toggleEmergencyAlert(false)
        }
    }

    fun toggleEmergencyAlert(toggle:Boolean){
        _homeScreenState.update {
            it.copy(
                emergencyAlertClosed = toggle
            )
        }

    }


}