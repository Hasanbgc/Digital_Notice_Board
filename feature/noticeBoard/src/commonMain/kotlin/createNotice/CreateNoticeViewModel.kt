package createNotice

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateNoticeViewModel: ViewModel() {

    val _createNoticeState = MutableStateFlow(CreateNoticeScreenState())
    val state = _createNoticeState.asStateFlow()

    fun onAction(action: CreateNoticeScreenAction) {
        when (action) {
           /* is CreateNoticeScreenAction.OnDismiss -> closeBottomSheet()*/
            is CreateNoticeScreenAction.BrowseAllCategory -> {}
            is CreateNoticeScreenAction.OnFilterClicked -> {}
            is CreateNoticeScreenAction.OnSearchQueryChanged -> updateTypeSearchQuery(action.query)
            is CreateNoticeScreenAction.PickedCategory -> navigateToNextStep(action.category)
            is CreateNoticeScreenAction.PublishNoticeClicked -> {}
            is CreateNoticeScreenAction.OnSearchClose -> {}
            is CreateNoticeScreenAction.OnBackPress -> {}
            is CreateNoticeScreenAction.OnCategoryChangeClicked -> navigateToPreviousStep()
        }
    }



    fun navigateToNextStep(category: Category) {
        _createNoticeState.update {
            it.copy(
                currentStep = NoticeCreationStep.ADD_NOTICE_BODY,
                selectedCategory = category
            )
        }
    }

    fun updateTypeSearchQuery(query: String) {
        _createNoticeState.update {
            it.copy(
                searchQuery = query,
                quickPickCategory =  categories.filter { category ->
                    category.title.contains(query, ignoreCase = true)
                }
            )
        }
    }
    fun navigateToPreviousStep(){
        _createNoticeState.update {
            it.copy(
                currentStep = NoticeCreationStep.QUICK_PICK_CATEGORY,
                selectedCategory = null
            )
        }
    }
}