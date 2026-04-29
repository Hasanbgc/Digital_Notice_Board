package com.hasan.dnb.auth

import com.hasan.dnb.domain.GoogleAccount
import com.hasan.dnb.domain.UserSession
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Flow<Result<GoogleAccount>>
    suspend fun signOutFromGoogle()
    fun observeAuthState(): Flow<UserSession>

}