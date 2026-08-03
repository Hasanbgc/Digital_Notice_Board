package com.hasan.dnb.domain

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val accessToken: String,
    val expiresIn: Int,
    val user: UserData
)

@Serializable
data class UserData(
    val name: String,
    val email: String,
    val avatar: String,
    val contactNumber: String,
)