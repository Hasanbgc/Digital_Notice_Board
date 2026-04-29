package com.hasan.dnb.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val uid: String,
    val displayName: String?,
    val photoUrl: String?,
    val contactNumber:String?

)
