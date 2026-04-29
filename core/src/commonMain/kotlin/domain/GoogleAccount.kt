package com.hasan.dnb.domain

data class GoogleAccount(
    val userId: String,
    val idToken: String,
    val displayName: String,
    val photoUrl: String?,
    val email: String?,
    val contactNumber: String?
){
    fun toUserSession() = UserSession(
        uid = userId,
        displayName = displayName,
        photoUrl = photoUrl,
        email = email,
        contactNumber = contactNumber
    )
}