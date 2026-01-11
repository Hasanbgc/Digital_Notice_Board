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
            is HomeScreenAction.OnLikeClicked -> updateLike(action.id, action.liked)
            is HomeScreenAction.OnShareClicked -> {}
            is HomeScreenAction.OnCommentClicked -> {}
            is HomeScreenAction.OnSavedClicked -> savePost(action.id)
            is HomeScreenAction.OnProfileClicked -> {}
            is HomeScreenAction.OnLocationClicked -> {}
            is HomeScreenAction.PostANoticeClicked -> {}
            is HomeScreenAction.OnNotificationClicked -> toggleEmergencyAlert(false)
            is HomeScreenAction.OnSearchQueryChanged -> updateQuery(action.query)
        }
    }

    fun toggleEmergencyAlert(toggle:Boolean){
        _homeScreenState.update {
            it.copy(
                emergencyAlertClosed = toggle
            )
        }

    }
    fun updateQuery(query:String) {
        _homeScreenState.update {
            it.copy(
                searchQuery = query
            )
        }
    }

    fun updateLike(id:Int, liked:Boolean){
        _homeScreenState.update {
            it.copy(
                poster = it.poster.map { poster ->
                    if (poster is Poster.Normal && poster.id == id) {
                        poster.copy(liked = if (liked)Like.LIKED else Like.UNLIKED ) // or !poster.liked to toggle
                    } else {
                        poster // Return unchanged poster
                    }
                }
            )
        }
    }

    fun savePost(id:Int){
        _homeScreenState.update {
            it.copy(
                poster = it.poster.map { poster ->
                    if (poster is Poster.Normal && poster.id == id) {
                        poster.copy(isSaved = !poster.isSaved) // or !poster.liked to toggle
                    } else {
                        poster // Return unchanged poster
                    }
                }
            )
        }
    }

}