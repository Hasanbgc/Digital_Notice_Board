package data

import com.hasan.dnb.auth.AuthRepository
import com.hasan.dnb.domain.GoogleAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class AuthRepositoryImpl : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): Flow<Result<GoogleAccount>> = flow {
        throw NotImplementedError("Google Sign-In not implemented on JVM yet")
    }
}
