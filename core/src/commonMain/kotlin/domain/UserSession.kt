package com.hasan.dnb.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
    val idToken: String,
)
