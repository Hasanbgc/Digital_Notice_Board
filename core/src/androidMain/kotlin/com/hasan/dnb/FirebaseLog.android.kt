package com.hasan.dnb

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics

actual fun firebaseLog(name: String) {
    Firebase.analytics.logEvent(name, null)
}
