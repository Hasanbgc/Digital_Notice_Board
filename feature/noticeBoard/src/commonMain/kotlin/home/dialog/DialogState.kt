package home.dialog

import domain.model.AttachmentItem

data class DialogState(
    val imageUrl: List<String> = emptyList(),
    val attachmentItems: List<AttachmentItem> = emptyList(),
    val imagePosition: Int = 0,
    val isDialogOpen: Boolean = false
)
