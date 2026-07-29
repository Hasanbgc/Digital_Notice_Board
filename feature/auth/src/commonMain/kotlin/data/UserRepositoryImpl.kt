package data

import com.hasan.dnb.auth.UserRepository
import com.hasan.dnb.domain.UserSession
import domain.ResultError
import domain.Results
import com.hasan.dnb.domain.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UserRepositoryImpl(
    private val httpClient: HttpClient
) : UserRepository {

    override suspend fun postUser(userSession: UserSession): Results<UserResponse, ResultError.Remote> {
        return safeCall {
            httpClient.post("/api/auth/register") {
                setBody(userSession)
            }
        }
    }
}
