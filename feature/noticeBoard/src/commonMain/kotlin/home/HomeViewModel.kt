package home

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import home.BottomSheet.Category
import home.BottomSheet.CreateNoticeScreenAction
import home.BottomSheet.CreateNoticeScreenState
import home.BottomSheet.NoticeCreationStep
import home.BottomSheet.categories
import home.dialog.DialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    val _homeScreenState = MutableStateFlow(HomeScreenState())
    val state = _homeScreenState.asStateFlow()

    val _dialogState = MutableStateFlow<DialogState?>(null)
    val dialogState = _dialogState.asStateFlow()

    val _createNoticeState = MutableStateFlow<CreateNoticeScreenState?>(null)
    val createNoticeState = _createNoticeState.asStateFlow()

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
            is HomeScreenAction.PostANoticeClicked -> openBottomSheet()
            is HomeScreenAction.OnNotificationClicked -> toggleEmergencyAlert(false)
            is HomeScreenAction.OnSearchQueryChanged -> updateQuery(action.query)
            is HomeScreenAction.OnDismissDialog -> dismissDialog()

        }
    }

    fun onCreateNoticeAction(action: CreateNoticeScreenAction) {
        when (action) {
            is CreateNoticeScreenAction.OnDismiss -> closeBottomSheet()
            is CreateNoticeScreenAction.BrowseAllCategory -> {}
            is CreateNoticeScreenAction.OnFilterClicked -> {}
            is CreateNoticeScreenAction.OnSearchQueryChanged -> updateTypeSearchQuery(action.query)
            is CreateNoticeScreenAction.PickedCategory -> navigateToNextStep(action.category)
            is CreateNoticeScreenAction.PublishNoticeClicked -> {}
            CreateNoticeScreenAction.OnSearchClose -> {}
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
        _homeScreenState.update {
            it.copy(
                poster = it.poster.map { poster ->
                    if (poster is Poster.Normal && poster.id == id) {
                        poster.copy(liked = if (liked) Like.LIKED else Like.UNLIKED) // or !poster.liked to toggle
                    } else {
                        poster // Return unchanged poster
                    }
                }
            )
        }
    }

    fun savePost(id: Int) {
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

    fun openBottomSheet() {
        _createNoticeState.value = CreateNoticeScreenState()
    }

    fun closeBottomSheet() {
        _createNoticeState.value = null
    }

    fun navigateToNextStep(category: Category) {
        _createNoticeState.update {
            it?.copy(
                currentStep = NoticeCreationStep.ADD_NOTICE_BODY,
                selectedCategory = category
            )
        }
    }

    fun updateTypeSearchQuery(query: String) {
        _createNoticeState.update {
            it?.copy(
                searchQuery = query,
                quickPickCategory =  categories.filter { category ->
                    category.title.contains(query, ignoreCase = true)
                }
            )
        }
    }
}