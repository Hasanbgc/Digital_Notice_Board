import androidx.compose.runtime.Composable

// Not implemented on iOS yet — see AttachmentPickers.ios.kt for the same rationale
// (no way to verify CoreLocation/CLLocationManager interop here). To implement:
// CLLocationManager().location (if already authorized) bridged via a delegate or
// polled from requestLocation(), returning null when authorization isn't granted.

@Composable
actual fun rememberCurrentLocationProvider(): suspend () -> Pair<Double, Double>? {
    return { null }
}
