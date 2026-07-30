package domain

import domain.model.ProfileResponse

interface Repository {
    suspend fun getProfile(): Results<ProfileResponse, ResultError.Remote>
}