package createNotice

sealed class CreateNoticeScreenAction {
    object OnDismiss : CreateNoticeScreenAction()
    object BrowseAllCategory : CreateNoticeScreenAction()
    object OnSearchClose : CreateNoticeScreenAction()
    object OnBackPress: CreateNoticeScreenAction()
    data class PickedCategory(val category: Category) : CreateNoticeScreenAction()
    data class OnFilterClicked(val category: ParentCategory) : CreateNoticeScreenAction()
    data class OnSearchQueryChanged(val query: String) : CreateNoticeScreenAction()
    object PublishNoticeClicked : CreateNoticeScreenAction()
    object OnCategoryChangeClicked: CreateNoticeScreenAction()
}
