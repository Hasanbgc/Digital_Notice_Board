package home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hasan.dnb.domain.Notice
import com.hasan.dnb.domain.UserSession
import com.hasan.dnb.location.LocationSource
import com.hasan.dnb.notice.NoticeRepository
import home.comment.CommentSheetState
import home.dialog.DialogState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

class HomeViewModel(
    userSession: UserSession,
    private val noticeRepository: NoticeRepository,
    private val locationSource: LocationSource
): ViewModel() {

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
        if (poster.noticeId.isBlank()) return
        viewModelScope.launch {
            noticeRepository.setSaved(poster.noticeId, !poster.isSaved)
        }
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

    private fun updateEmergencyNotices(realEmergencyNotices: List<Poster.Emergency>) {
        _homeScreenState.update {
            it.copy(emergencyNotice = realEmergencyNotices)
        }
    }

    private val userLocation = MutableStateFlow<Pair<Double, Double>?>(null)

    init {
        updateEmergencyNotices(emptyList())
        observeMyNotices()
        observeSavedNotices()
        fetchUserLocation()
    }

    private fun observeSavedNotices() {
        viewModelScope.launch {
            noticeRepository.observeSavedNotices().collect { notices ->
                updateSavedNotices(notices.map { it.toPosterNormal() })
            }
        }
    }

    private fun fetchUserLocation() {
        viewModelScope.launch {
            userLocation.value = locationSource.getCurrentLocation()
        }
    }

    private fun observeMyNotices() {
        viewModelScope.launch {
            combine(
                noticeRepository.observeNotices(),
                userLocation
            ) { notices, location -> notices to location }
                .collect { (notices, location) ->
                    val (emergency, normal) = notices.partition { it.isEmergency }
                    updateMyNotices(normal.map { it.toPosterNormal() })
                    updateEmergencyNotices(emergency.map { it.toPosterEmergency() })
                    updateNearbyNotices(buildNearbyNotices(normal, location))
                }
        }
    }

    private fun buildNearbyNotices(
        notices: List<Notice>,
        userLocation: Pair<Double, Double>?
    ): List<Poster.Normal> {
        if (userLocation == null) return emptyList()
        val (userLat, userLon) = userLocation
        return notices
            .mapNotNull { notice ->
                val lat = notice.latitude ?: return@mapNotNull null
                val lon = notice.longitude ?: return@mapNotNull null
                val distanceKm = haversineDistanceKm(userLat, userLon, lat, lon)
                if (distanceKm > NEARBY_RADIUS_KM) null else notice to distanceKm
            }
            .sortedBy { (_, distanceKm) -> distanceKm }
            .map { (notice, distanceKm) -> notice.toPosterNormal(distanceKm) }
    }

}

private const val EARTH_RADIUS_KM = 6371.0
private const val NEARBY_RADIUS_KM = 50.0

private fun Double.toRadians(): Double = this * PI / 180.0

private fun haversineDistanceKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val dLat = (lat2 - lat1).toRadians()
    val dLon = (lon2 - lon1).toRadians()
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(lat1.toRadians()) * cos(lat2.toRadians()) * sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return EARTH_RADIUS_KM * c
}

private fun formatDistance(km: Double): String {
    return if (km < 1.0) {
        "${(km * 1000).toInt()}m away"
    } else {
        "${round(km * 10) / 10.0}km away"
    }
}

private fun Notice.toPosterNormal(distanceKm: Double? = null): Poster.Normal {
    val imageUrls = attachments.filter { it.type == "IMAGE" }.mapNotNull { it.uri }
    // AttachmentCard renders these as downloadable-document chips, so only real files
    // (PDFs) belong here — not the LOCATION attachment (not a file) or VIDEO (no
    // dedicated video UI in the feed yet).
    val fileNames = attachments.filter { it.type == "PDF" }.map { it.name }
    return Poster.Normal(
        id = id.hashCode(),
        noticeId = id,
        title = title,
        description = details,
        date = "",
        distance = distanceKm?.let { formatDistance(it) } ?: "",
        time = "Just now",
        imageUrlList = imageUrls,
        location = locationText ?: "",
        type = Type.NORMAL,
        category = categoryTitle,
        profile = Profile(
            name = "You",
            imageUrl = "",
            institution = categoryTitle,
            designation = "Notice"
        ),
        attachments = fileNames,
        isFavorite = false,
        shareCount = 0,
        commentCount = 0,
        likeCount = 0,
        isSaved = isSaved,
        viewCount = 0,
        isExpanded = false,
        liked = Like.IDLE
    )
}

private fun Notice.toPosterEmergency(): Poster.Emergency {
    val imageUrl = attachments.firstOrNull { it.type == "IMAGE" }?.uri ?: ""
    return Poster.Emergency(
        id = id.hashCode(),
        title = title,
        description = details,
        date = "",
        distance = "",
        time = "Just now",
        imageUrl = imageUrl,
        location = locationText ?: "",
        type = Type.HIGH,
        // Most Topic values don't have an icon asset yet (see HomeScreen.getIcon) —
        // ACCIDENT is one of the few implemented, so it's used as a safe generic fallback
        // until user-published emergency notices can carry their own topic.
        topic = Topic.ACCIDENT
    )
}

