package com.hasan.dnb.auth

import com.hasan.dnb.domain.UserResponse
import com.hasan.dnb.domain.UserSession
import domain.ResultError
import domain.Results

interface UserRepository {
    suspend fun postUser(userSession: UserSession): Results<UserResponse, ResultError.Remote>
}
