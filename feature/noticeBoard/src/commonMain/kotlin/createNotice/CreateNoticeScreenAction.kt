package createNotice

sealed class CreateNoticeScreenAction {
    object BrowseAllCategory : CreateNoticeScreenAction()
    object OnSearchClose : CreateNoticeScreenAction()
    object OnBackPress: CreateNoticeScreenAction()
    data class PickedCategory(val category: Category) : CreateNoticeScreenAction()
    data class OnFilterClicked(val category: ParentCategory) : CreateNoticeScreenAction()
    data class OnSearchQueryChanged(val query: String) : CreateNoticeScreenAction()
    object PublishNoticeClicked : CreateNoticeScreenAction()
    object OnCategoryChangeClicked: CreateNoticeScreenAction()
    data class OnAttachmentsAdded(val attachments: List<Attachment>) : CreateNoticeScreenAction()
    data class OnRemoveAttachment(val id: String) : CreateNoticeScreenAction()
    data class OnManageAttachmentsClicked(val type: AttachmentType) : CreateNoticeScreenAction()
    data class OnTitleChanged(val title: String) : CreateNoticeScreenAction()
    data class OnDetailsChanged(val details: String) : CreateNoticeScreenAction()
}
