package home.dialog

data class DialogState(
    val imageUrl:List<String> = emptyList(),
    val imagePosition:Int = 0,
    val isDialogOpen:Boolean = false
)
