package com.hasan.dnb.location

interface LocationSource {
    /**
     * Best-effort, no-prompt read of the device's last known location.
     * Returns null if permission hasn't been granted or no location is available yet.
     */
    suspend fun getCurrentLocation(): Pair<Double, Double>?

    /**
     * Resolves a coordinate pair into a short, human-readable landmark/area description
     * (e.g. "Dhanmondi, Dhaka") instead of raw numbers. Returns null if it can't be resolved
     * (no geocoder available, no network, or nothing found).
     */
    suspend fun reverseGeocode(latitude: Double, longitude: Double): String?
}
