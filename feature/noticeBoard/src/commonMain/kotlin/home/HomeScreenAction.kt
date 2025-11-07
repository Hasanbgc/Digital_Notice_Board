package home

sealed interface HomeScreenAction {
    object OnEmergencyAlertDismiss: HomeScreenAction
    data class OnEmergencyPosterClicked(val id:Int,val isExpanded: Boolean): HomeScreenAction
    data class OnImageClicked(val id:Int): HomeScreenAction
    data class OnLikeClicked(val id:Int): HomeScreenAction
    data class OnShareClicked(val id:Int): HomeScreenAction
    data class OnCommentClicked(val id:Int): HomeScreenAction
    object OnSavedClicked: HomeScreenAction
    data class OnProfileClicked(val id:Int): HomeScreenAction
    data class OnLocationClicked(val id:Int): HomeScreenAction
    object PostANoticeClicked: HomeScreenAction

}