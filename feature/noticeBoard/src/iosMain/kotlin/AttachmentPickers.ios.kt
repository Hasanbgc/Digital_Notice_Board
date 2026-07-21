import androidx.compose.runtime.Composable

// iOS pickers aren't wired up yet — this mirrors AppMapView.ios.kt's approach of
// shipping a placeholder rather than an unverified PhotosUI/UIDocumentPicker
// integration. To implement: present a PHPickerViewController (images/videos) or
// UIDocumentPickerViewController (PDF) from LocalUIViewController.current, bridge
// its delegate callbacks back into `onPicked`, and load NSItemProvider contents into
// a sandbox-accessible file URL before mapping to PickedFile.

@Composable
actual fun rememberImagePickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    return { /* not yet implemented on iOS */ }
}

@Composable
actual fun rememberVideoPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    return { /* not yet implemented on iOS */ }
}

@Composable
actual fun rememberPdfPickerLauncher(onPicked: (List<PickedFile>) -> Unit): () -> Unit {
    return { /* not yet implemented on iOS */ }
}

@Composable
actual fun rememberVideoPlaybackLauncher(): (uri: String) -> Unit {
    return { /* not yet implemented on iOS */ }
}

@Composable
actual fun rememberPdfOpenLauncher(): (uri: String) -> Unit {
    return { /* not yet implemented on iOS */ }
}
