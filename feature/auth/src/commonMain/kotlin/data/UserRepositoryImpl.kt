package data

import com.hasan.dnb.auth.UserRepository
import com.hasan.dnb.domain.UserResponse
import com.hasan.dnb.domain.UserSession
import com.hasan.dnb.session.Session
import com.hasan.dnb.session.SessionManager
import domain.ResultError
import domain.Results
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UserRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionManager: SessionManager
) : UserRepository {

    override suspend fun postUser(userSession: UserSession): Results<UserResponse, ResultError.Remote> {
        val result = safeCall<UserResponse> {
            httpClient.post("/api/auth/register") {
                setBody(userSession)
            }
        }
        if (result is Results.Success) {
            sessionManager.save(Session(accessToken = result.data.accessToken))
        }
        return result
    }
}
