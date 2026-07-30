package data

import domain.Repository
import domain.ResultError
import domain.Results
import domain.model.ProfileResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import presentation.loge

class RepositoryImpl(
    private val httpClient: HttpClient,
): Repository {
    override suspend fun getProfile(): Results<ProfileResponse, ResultError.Remote> {
        val response =  safeCall<ProfileResponse> {
            httpClient.get("/api/auth/profile")
        }
        if(response is Results.Success){
            "profile_name".loge(response.data.name)
        }
        return response
    }
}