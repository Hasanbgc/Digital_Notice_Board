package home

sealed interface HomeScreenAction {
    object OnEmergencyAlertDismiss: HomeScreenAction
    data class OnEmergencyPosterClicked(val id:Int,val isExpanded: Boolean): HomeScreenAction
    data class OnImageClicked(val imageList:List<String>,val id:Int): HomeScreenAction
    data class OnLikeClicked(val id:Int,val liked:Boolean = false): HomeScreenAction
    data class OnShareClicked(val id:Int): HomeScreenAction
    data class OnCommentClicked(val id:Int): HomeScreenAction
    data class OnSavedClicked(val id:Int): HomeScreenAction
    data class OnProfileClicked(val id:Int): HomeScreenAction
    data class OnLocationClicked(val id:Int): HomeScreenAction
    object PostANoticeClicked: HomeScreenAction
    data object OnNotificationClicked: HomeScreenAction
    data class OnSearchQueryChanged(val query:String): HomeScreenAction
    object OnDismissDialog: HomeScreenAction
}