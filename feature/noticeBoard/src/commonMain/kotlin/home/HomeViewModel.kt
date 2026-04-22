package home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import home.comment.CommentSheetState
import home.dialog.DialogState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(uid: String): ViewModel() {

    val _homeScreenState = MutableStateFlow(HomeScreenState())
    val state = _homeScreenState.asStateFlow()

    val _dialogState = MutableStateFlow<DialogState?>(null)
    val dialogState = _dialogState.asStateFlow()

    val _commentSheetState = MutableStateFlow<CommentSheetState?>(null)
    val commentSheetState = _commentSheetState.asStateFlow()

    private val updatedLiked = MutableStateFlow<Map<Int, Boolean>>(emptyMap())

    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.OnEmergencyAlertDismiss -> toggleEmergencyAlert(true)
            is HomeScreenAction.OnEmergencyPosterClicked -> {}
            is HomeScreenAction.OnImageClicked -> showImageDialog(action.imageList, action.id)
            is HomeScreenAction.OnLikeClicked -> updateLike(action.id, action.liked)
            is HomeScreenAction.OnShareClicked -> {}
            is HomeScreenAction.OnCommentClicked -> openCommentSheet(action.id)
            is HomeScreenAction.OnCommentInputChanged -> updateCommentInput(action.text)
            HomeScreenAction.OnCommentSubmit -> submitComment()
            HomeScreenAction.OnDismissCommentSheet -> _commentSheetState.value = null
            is HomeScreenAction.OnSavedClicked -> savePost(action.poster)
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
        updatedLiked.update {
            it + (id to liked)
        }
    }

    fun savePost(poster: Poster.Normal) {
        addSavedNote(poster)
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

    private fun openCommentSheet(postId: Int) {
        _commentSheetState.value = CommentSheetState(
            postId = postId,
            comments = getDummyComments(postId)
        )
    }

    private fun updateCommentInput(text: String) {
        _commentSheetState.update { it?.copy(inputText = text) }
    }

    private fun submitComment() {
        val current = _commentSheetState.value ?: return
        val text = current.inputText.trim()
        if (text.isBlank()) return
        val newComment = Comment(
            id = current.comments.size + 1,
            authorName = "You",
            authorImageUrl = "",
            text = text,
            time = "Just now"
        )
        _commentSheetState.update {
            it?.copy(
                comments = it.comments + newComment,
                inputText = ""
            )
        }
    }

    private fun getDummyComments(postId: Int): List<Comment> = listOf(
        Comment(
            1,
            "Anika Chowdhury",
            "https://picsum.photos/id/${postId + 10}/100/100",
            "Thanks for sharing this important update!",
            "2 min ago"
        ),
        Comment(
            2,
            "Rahim Uddin",
            "https://picsum.photos/id/${postId + 20}/100/100",
            "Very helpful information, please keep us posted.",
            "8 min ago"
        ),
        Comment(
            3,
            "Fatema Begum",
            "https://picsum.photos/id/${postId + 30}/100/100",
            "I saw this earlier today near my area too.",
            "15 min ago"
        ),
    )


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

    val forYouUpdatedFlow: Flow<PagingData<Poster.Normal>> = combine(
        forYouFlow,
        updatedLiked
    ) { pagingData, update ->
        pagingData.map { poster ->
            val update = update[poster.id]
            if (update != null) {
                val (count: Int, liked: Like) = if (update) {
                    poster.likeCount + 1 to Like.LIKED
                } else {
                    poster.likeCount to Like.UNLIKED
                }
                poster.copy(likeCount = count, liked = liked)
            } else {
                poster
            }
        }
    }

    fun getEmergencyNotices() {
        _homeScreenState.update {
            it.copy(
                emergencyNotice = listOf(
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


