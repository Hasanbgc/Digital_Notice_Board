import androidx.compose.runtime.Composable

/**
 * A file selected by the user through a platform picker, before it becomes an [createNotice.Attachment].
 */
data class PickedFile(
    val uri: String,
    val name: String
)

/**
 * Returns a trigger function that opens the platform's native image picker
 * (multi-select) and reports the chosen files via [onPicked].
 */
@Composable
expect fun rememberImagePickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit

/**
 * Returns a trigger function that opens the platform's native video picker
 * (multi-select) and reports the chosen files via [onPicked].
 */
@Composable
expect fun rememberVideoPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit

/**
 * Returns a trigger function that opens the platform's native document picker
 * filtered to PDF files (multi-select) and reports the chosen files via [onPicked].
 */
@Composable
expect fun rememberPdfPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit

/**
 * Returns a function that plays/opens the video at the given uri in the platform's
 * native video player.
 */
@Composable
expect fun rememberVideoPlaybackLauncher(): (uri: String) -> Unit

/**
 * Returns a function that opens the PDF at the given uri in the platform's native
 * document viewer.
 */
@Composable
expect fun rememberPdfOpenLauncher(): (uri: String) -> Unit
