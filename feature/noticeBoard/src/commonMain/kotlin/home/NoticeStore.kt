package home

// Notices the user has bookmarked, backed by Room and kept in sync by HomeViewModel.
val savedNotices = mutableListOf<Poster.Normal>()
private var onSavedNoticesChanged: (() -> Unit)? = null

fun setOnSavedListChangedListener(listener: () -> Unit) {
    onSavedNoticesChanged = listener
}

fun updateSavedNotices(notices: List<Poster.Normal>) {
    savedNotices.clear()
    savedNotices.addAll(notices)
    onSavedNoticesChanged?.invoke()
}

// Notices the user has published, backed by Room and kept in sync by HomeViewModel.
val myNotices = mutableListOf<Poster.Normal>()
private var onMyNoticesChanged: (() -> Unit)? = null

fun setOnMyNoticesChangedListener(listener: () -> Unit) {
    onMyNoticesChanged = listener
}

fun updateMyNotices(notices: List<Poster.Normal>) {
    myNotices.clear()
    myNotices.addAll(notices)
    onMyNoticesChanged?.invoke()
}

// Real notices within range of the user's last known location, sorted nearest-first.
val nearbyNotices = mutableListOf<Poster.Normal>()
private var onNearbyNoticesChanged: (() -> Unit)? = null

fun setOnNearbyNoticesChangedListener(listener: () -> Unit) {
    onNearbyNoticesChanged = listener
}

fun updateNearbyNotices(notices: List<Poster.Normal>) {
    nearbyNotices.clear()
    nearbyNotices.addAll(notices)
    onNearbyNoticesChanged?.invoke()
}
