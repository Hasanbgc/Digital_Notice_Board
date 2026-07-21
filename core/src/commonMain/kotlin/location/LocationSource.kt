package com.hasan.dnb.location

interface LocationSource {
    /**
     * Best-effort, no-prompt read of the device's last known location.
     * Returns null if permission hasn't been granted or no location is available yet.
     */
    suspend fun getCurrentLocation(): Pair<Double, Double>?
}
