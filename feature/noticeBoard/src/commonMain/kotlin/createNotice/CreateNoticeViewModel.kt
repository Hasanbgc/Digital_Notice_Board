package createNotice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasan.dnb.domain.Notice
import com.hasan.dnb.domain.NoticeAttachment
import com.hasan.dnb.notice.NoticeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class CreateNoticeViewModel(
    private val noticeRepository: NoticeRepository
) : ViewModel() {

    val _createNoticeState = MutableStateFlow(CreateNoticeScreenState())
    val state = _createNoticeState.asStateFlow()

    fun onAction(action: CreateNoticeScreenAction) {
        when (action) {
            is CreateNoticeScreenAction.BrowseAllCategory -> {}
            is CreateNoticeScreenAction.OnFilterClicked -> {}
            is CreateNoticeScreenAction.OnSearchQueryChanged -> updateTypeSearchQuery(action.query)
            is CreateNoticeScreenAction.PickedCategory -> navigateToNextStep(action.category)
            is CreateNoticeScreenAction.PublishNoticeClicked -> publishNotice()
            is CreateNoticeScreenAction.OnSearchClose -> {}
            is CreateNoticeScreenAction.OnBackPress -> {}
            is CreateNoticeScreenAction.OnCategoryChangeClicked -> navigateToPreviousStep()
            is CreateNoticeScreenAction.OnAttachmentsAdded -> addAttachments(action.attachments)
            is CreateNoticeScreenAction.OnRemoveAttachment -> removeAttachment(action.id)
            is CreateNoticeScreenAction.OnManageAttachmentsClicked -> openAttachmentManager(action.type)
            is CreateNoticeScreenAction.OnTitleChanged -> updateTitle(action.title)
            is CreateNoticeScreenAction.OnDetailsChanged -> updateDetails(action.details)
        }
    }

    private fun updateTitle(title: String) {
        _createNoticeState.update { it.copy(title = title) }
    }

    private fun updateDetails(details: String) {
        _createNoticeState.update { it.copy(details = details) }
    }

    private fun publishNotice() {
        val current = _createNoticeState.value
        val category = current.selectedCategory ?: return
        val location = current.attachments
            .firstOrNull { it.type == AttachmentType.LOCATION }
            ?.uri
            ?.let { uri ->
                val parts = uri.split(",")
                val lat = parts.getOrNull(0)?.toDoubleOrNull()
                val lon = parts.getOrNull(1)?.toDoubleOrNull()
                if (lat != null && lon != null) lat to lon else null
            }
        val notice = Notice(
            id = Random.nextLong().toString(),
            title = current.title,
            details = current.details,
            categoryId = category.id,
            categoryTitle = category.title,
            attachments = current.attachments.map { it.toNoticeAttachment() },
            createdAt = currentTimeMillis(),
            isEmergency = category.parentCategory?.id == emergencyParent.id,
            latitude = location?.first,
            longitude = location?.second
        )
        viewModelScope.launch {
            try {
                noticeRepository.saveNotice(notice)
                _createNoticeState.update { CreateNoticeScreenState() }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Keep the draft intact so the user doesn't lose their work if saving fails.
            }
        }
    }

    private fun addAttachments(newAttachments: List<Attachment>) {
        _createNoticeState.update {
            it.copy(attachments = it.attachments + newAttachments)
        }
    }

    private fun removeAttachment(id: String) {
        _createNoticeState.update {
            it.copy(attachments = it.attachments.filterNot { attachment -> attachment.id == id })
        }
    }

    private fun openAttachmentManager(type: AttachmentType) {}



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

private fun Attachment.toNoticeAttachment() = NoticeAttachment(
    id = id,
    name = name,
    type = type.name,
    uri = uri
)
