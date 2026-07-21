import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.io.File
import java.net.URI
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

@Composable
actual fun rememberImagePickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    return {
        pickFiles(
            title = "Select Images",
            extensions = arrayOf("jpg", "jpeg", "png", "webp", "gif", "bmp"),
            onPicked = onPicked
        )
    }
}

@Composable
actual fun rememberVideoPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    return {
        pickFiles(
            title = "Select Videos",
            extensions = arrayOf("mp4", "mov", "mkv", "webm", "avi"),
            onPicked = onPicked
        )
    }
}

@Composable
actual fun rememberPdfPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    return {
        pickFiles(
            title = "Select PDF Documents",
            extensions = arrayOf("pdf"),
            onPicked = onPicked
        )
    }
}

private fun pickFiles(title: String, extensions: Array<String>, onPicked: (List<PickedFile>) -> Unit) {
    val chooser = JFileChooser().apply {
        dialogTitle = title
        fileSelectionMode = JFileChooser.FILES_ONLY
        isMultiSelectionEnabled = true
        fileFilter = FileNameExtensionFilter(title, *extensions)
    }
    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        val files = chooser.selectedFiles.takeIf { it.isNotEmpty() }
            ?: chooser.selectedFile?.let { arrayOf(it) }
            ?: emptyArray()
        onPicked(files.map { it.toPickedFile() })
    }
}

private fun File.toPickedFile() = PickedFile(uri = toURI().toString(), name = name)

@Composable
actual fun rememberVideoPlaybackLauncher(): (uri: String) -> Unit = { uri -> openWithSystemViewer(uri) }

@Composable
actual fun rememberPdfOpenLauncher(): (uri: String) -> Unit = { uri -> openWithSystemViewer(uri) }

private fun openWithSystemViewer(uri: String) {
    try {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
            Desktop.getDesktop().open(File(URI(uri)))
        }
    } catch (_: Exception) {
        // No associated viewer or the file couldn't be opened; nothing sensible to do here.
    }
}
