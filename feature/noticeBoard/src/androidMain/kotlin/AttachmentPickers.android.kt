import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImagePickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { context.tryPersistReadPermission(it) }
            onPicked(uris.map { it.toPickedFile(context) })
        }
    }
    return {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}

@Composable
actual fun rememberVideoPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { context.tryPersistReadPermission(it) }
            onPicked(uris.map { it.toPickedFile(context) })
        }
    }
    return {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
    }
}

@Composable
actual fun rememberPdfPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { context.tryPersistReadPermission(it) }
            onPicked(uris.map { it.toPickedFile(context) })
        }
    }
    return {
        context.maybeShowMultiSelectHint()
        launcher.launch(arrayOf("application/pdf"))
    }
}

/**
 * Photo Picker URIs (API 33+) reject persistable grants outright — they don't need one,
 * their read access already survives app/process restarts. Plain SAF document-picker URIs
 * (what [ActivityResultContracts.PickMultipleVisualMedia] and [ActivityResultContracts.OpenMultipleDocuments]
 * fall back to below API 33, or always for documents) only keep their grant for the
 * lifetime of the launching activity unless we persist it — without this, a picked
 * image/video/PDF can render fine immediately but fail to load later (e.g. from the
 * Home feed) once that grant is gone.
 */
private fun Context.tryPersistReadPermission(uri: Uri) {
    try {
        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } catch (_: SecurityException) {
        // Doesn't support (or doesn't need) a persistable grant; nothing else to do.
    }
}

/**
 * Android's system document picker can select multiple files, but it opens in
 * single-tap-to-confirm mode by default — the user has to tap the picker's own
 * "select multiple" icon first. We can't force that UI open in multi-select mode
 * (it's controlled by the document provider, not our Intent), so we nudge the user
 * once instead.
 */
private fun Context.maybeShowMultiSelectHint() {
    val prefs = getSharedPreferences("attachment_picker_prefs", Context.MODE_PRIVATE)
    val key = "pdf_multiselect_hint_shown"
    if (!prefs.getBoolean(key, false)) {
        Toast.makeText(
            this,
            "Tip: tap the \"select multiple\" icon in the file picker to choose several PDFs at once.",
            Toast.LENGTH_LONG
        ).show()
        prefs.edit().putBoolean(key, true).apply()
    }
}

private fun Uri.toPickedFile(context: Context): PickedFile {
    val displayName = context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex >= 0 && cursor.moveToFirst()) cursor.getString(nameIndex) else null
    }
    return PickedFile(uri = toString(), name = displayName ?: "Untitled")
}

@Composable
actual fun rememberVideoPlaybackLauncher(): (uri: String) -> Unit {
    val context = LocalContext.current
    return { uri -> context.openWithViewer(uri, "video/*") }
}

@Composable
actual fun rememberPdfOpenLauncher(): (uri: String) -> Unit {
    val context = LocalContext.current
    return { uri -> context.openWithViewer(uri, "application/pdf") }
}

private fun Context.openWithViewer(uri: String, mimeType: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(uri), mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(intent)
    } catch (_: android.content.ActivityNotFoundException) {
        // No app available to open this file; nothing sensible to do here.
    }
}
