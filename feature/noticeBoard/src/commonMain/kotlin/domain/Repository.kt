package domain

import domain.model.PostResponse
import domain.model.ProfileResponse

interface Repository {
    suspend fun getProfile(): Results<ProfileResponse, ResultError.Remote>
    suspend fun getPosts(): Results<List<PostResponse>, ResultError.Remote>
}