package com.hasan.dnb.domain

data class GoogleAccount(
    val userId: String,
    val idToken: String,
    val email: String,
    val displayName: String,
    val photoUrl: String?,
    val contactNumber: String?
){
    fun toUserSession() = UserSession(
        idToken = idToken,
    )
}