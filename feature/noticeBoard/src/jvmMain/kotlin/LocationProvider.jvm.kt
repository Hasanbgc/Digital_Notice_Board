import androidx.compose.runtime.Composable

// No desktop geolocation API wired up; auto-pick silently no-ops here, same as
// how AttachmentPickers.jvm.kt handles platform-only features.

@Composable
actual fun rememberCurrentLocationProvider(): suspend () -> Pair<Double, Double>? {
    return { null }
}
