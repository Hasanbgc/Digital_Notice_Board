package presentation

sealed interface UiEvent {
    data object NavigateBack : UiEvent
    data object Navigate : UiEvent
    data class ShowToast(val message: String) : UiEvent
    data class ShowSnackbar(
        val message: UiText,
        val action: SnackbarAction? = null
    ) : UiEvent
}

data class SnackbarAction(
    val label: String,
    val action: () -> Unit
)