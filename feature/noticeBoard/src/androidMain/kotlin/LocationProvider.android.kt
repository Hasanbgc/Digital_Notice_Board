import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
actual fun rememberCurrentLocationProvider(): suspend () -> Pair<Double, Double>? {
    val context = LocalContext.current
    return { context.getLastKnownLocationOrNull() }
}

/**
 * Shared by [rememberCurrentLocationProvider] and [data.LocationSourceImpl] so both the
 * create-notice publish flow and the home feed's nearby filtering use the exact same
 * best-effort, no-prompt location read.
 */
internal suspend fun Context.getLastKnownLocationOrNull(): Pair<Double, Double>? {
    if (!hasLocationPermission()) return null

    return try {
        suspendCancellableCoroutine { continuation ->
            LocationServices.getFusedLocationProviderClient(this)
                .lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(location?.let { it.latitude to it.longitude })
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    } catch (_: SecurityException) {
        null
    }
}

private fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}
