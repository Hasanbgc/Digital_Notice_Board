package home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.asState
import androidx.paging.cachedIn
import home.dialog.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class HomeViewModel : ViewModel() {

    val _homeScreenState = MutableStateFlow(HomeScreenState())
    val state = _homeScreenState.asStateFlow()

    val _dialogState = MutableStateFlow<DialogState?>(null)
    val dialogState = _dialogState.asStateFlow()


    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.OnEmergencyAlertDismiss -> toggleEmergencyAlert(true)
            is HomeScreenAction.OnEmergencyPosterClicked -> {}
            is HomeScreenAction.OnImageClicked -> showImageDialog(action.imageList, action.id)
            is HomeScreenAction.OnLikeClicked -> updateLike(action.id, action.liked)
            is HomeScreenAction.OnShareClicked -> {}
            is HomeScreenAction.OnCommentClicked -> {}
            is HomeScreenAction.OnSavedClicked -> savePost(action.id)
            is HomeScreenAction.OnProfileClicked -> {}
            is HomeScreenAction.OnLocationClicked -> {}
            /*is HomeScreenAction.PostANoticeClicked -> navigateToCreateNotice()*/
            is HomeScreenAction.OnNotificationClicked -> toggleEmergencyAlert(false)
            is HomeScreenAction.OnSearchQueryChanged -> updateQuery(action.query)
            is HomeScreenAction.OnDismissDialog -> dismissDialog()
            is HomeScreenAction.OnTopReached -> {
//                if (_homeScreenState.value.poster.isNotEmpty()) {
//                    pager.prepend()
//                }
            }

            is HomeScreenAction.OnBottomReached -> {
                /*if (_homeScreenState.value.poster.isNotEmpty()) {
                    pager.append()
                }*/
            }
            else -> {}
        }
    }

    fun toggleEmergencyAlert(toggle: Boolean) {
        _homeScreenState.update {
            it.copy(
                emergencyAlertClosed = toggle
            )
        }

    }

    fun updateQuery(query: String) {
        _homeScreenState.update {
            it.copy(
                searchQuery = query
            )
        }
    }

    fun updateLike(id: Int, liked: Boolean) {
        /*_homeScreenState.update {
            it.copy(
                poster = it.poster.map { poster ->
                    if (poster is Poster.Normal && poster.id == id) {
                        poster.copy(liked = if (liked) Like.LIKED else Like.UNLIKED) // or !poster.liked to toggle
                    } else {
                        poster // Return unchanged poster
                    }
                }
            )
        }*/
    }

    fun savePost(id: Int) {
        /*_homeScreenState.update {
            it.copy(
                poster = it.poster.map { poster ->
                    if (poster is Poster.Normal && poster.id == id) {
                        poster.copy(isSaved = !poster.isSaved) // or !poster.liked to toggle
                    } else {
                        poster // Return unchanged poster
                    }
                }
            )
        }*/
    }

    fun showImageDialog(imageList: List<String>, index: Int) {
        _dialogState.value = DialogState(
            imageUrl = imageList,
            imagePosition = index,
            isDialogOpen = true
        )
    }

    fun dismissDialog() {
        _dialogState.value = null
    }


    val forYouFlow = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            PosterPagingSource(PosterPagingSource.FOR_YOU)
        }
    ).flow.cachedIn(viewModelScope)

    val nearByFlow = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2,
        ),
        pagingSourceFactory = {
            PosterPagingSource(PosterPagingSource.NEARBY)
        }
    ).flow.cachedIn(viewModelScope)
    val savedFlow = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 2,
        ),
        pagingSourceFactory = {
            PosterPagingSource(PosterPagingSource.SAVED)
        }
    ).flow.cachedIn(viewModelScope)

    fun getEmergencyNotices() {
        _homeScreenState.update {
            it.copy(
                emergencyNotice =  listOf(
                    Poster.Emergency(
                        1,
                        "Flash Flood Warning, please stay away form there",
                        "Heavy rainfall causing flood in low-laying areas. lorem ipsum dolor sit amet, consectetur adipiscing elit.  ",
                        "5/11/2025",
                        "2km away",
                        "5 min ago",
                        "",
                        "Dhanmondi Area, Near Dhaka University",
                        Type.HIGH,
                        Topic.FIRE
                    ),
                    Poster.Emergency(
                        2,
                        "Gas Leak Alert in Residential Area",
                        "A major gas leakage has been reported from an underground pipeline. Residents are advised to evacuate immediately and avoid using electrical switches.",
                        "6/11/2025",
                        "800m away",
                        "10 min ago",
                        "",
                        "Mirpur Section 10, Near Bus Stand",
                        Type.MEDIUM,
                        Topic.GAS_LEAK
                    ),
                    Poster.Emergency(
                        3,
                        "Road Accident Warning",
                        "Multiple vehicles involved in a collision causing traffic congestion. Emergency services are on the way. Please use alternative routes.",
                        "6/11/2025",
                        "3.5km away",
                        "18 min ago",
                        "",
                        "Mohakhali Flyover, Dhaka",
                        Type.MEDIUM,
                        Topic.ACCIDENT
                    )
                )
            )
        }

    }

    init {
        getEmergencyNotices()
    }

}


