package home.comment

import home.Comment

data class CommentSheetState(
    val postId: Int,
    val comments: List<Comment> = emptyList(),
    val inputText: String = "",
    val isLoading: Boolean = false
)
