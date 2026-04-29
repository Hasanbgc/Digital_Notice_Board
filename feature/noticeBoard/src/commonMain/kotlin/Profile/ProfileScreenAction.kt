package Profile

sealed interface ProfileScreenAction {
    data object OnLogoutClicked : ProfileScreenAction
    data object OnLogoutConfirmed : ProfileScreenAction
    data object OnLogoutCancelled : ProfileScreenAction
}
