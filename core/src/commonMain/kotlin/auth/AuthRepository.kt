package com.hasan.dnb.auth

import com.hasan.dnb.domain.GoogleAccount
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Flow<Result<GoogleAccount>>

    fun checkUserSession(): String
}