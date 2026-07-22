package data

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.hasan.dnb.location.LocationSource
import getLastKnownLocationOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class LocationSourceImpl(private val context: Context) : LocationSource {
    override suspend fun getCurrentLocation(): Pair<Double, Double>? {
        return context.getLastKnownLocationOrNull()
    }

    override suspend fun reverseGeocode(latitude: Double, longitude: Double): String? {
        return try {
            withContext(Dispatchers.IO) {
                @Suppress("DEPRECATION")
                val addresses = Geocoder(context, Locale.getDefault())
                    .getFromLocation(latitude, longitude, 1)
                addresses?.firstOrNull()?.let { it.toLandmarkText() }
            }
        } catch (_: Exception) {
            // No geocoder backend, no network, or nothing found — degrade gracefully.
            null
        }
    }
}

private fun Address.toLandmarkText(): String? {
    val parts = listOfNotNull(
        subLocality,
        locality ?: subAdminArea
    ).filter { it.isNotBlank() }

    if (parts.isNotEmpty()) return parts.joinToString(", ")

    return getAddressLine(0)?.takeIf { it.isNotBlank() }
}
