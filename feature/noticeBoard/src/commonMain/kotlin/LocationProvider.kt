import androidx.compose.runtime.Composable

/**
 * Returns a function that attempts to silently read the device's last known
 * location — no permission prompt, no UI. Returns null if location permission
 * hasn't already been granted (e.g. from a previous manual location pick) or no
 * location is available yet, so callers should treat this as best-effort.
 */
@Composable
expect fun rememberCurrentLocationProvider(): suspend () -> Pair<Double, Double>?
