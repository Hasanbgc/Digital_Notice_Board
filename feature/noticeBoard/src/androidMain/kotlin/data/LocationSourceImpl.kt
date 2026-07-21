package data

import android.content.Context
import com.hasan.dnb.location.LocationSource
import getLastKnownLocationOrNull

class LocationSourceImpl(private val context: Context) : LocationSource {
    override suspend fun getCurrentLocation(): Pair<Double, Double>? {
        return context.getLastKnownLocationOrNull()
    }
}
