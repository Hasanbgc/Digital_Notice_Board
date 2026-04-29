package data

import com.google.firebase.auth.FirebaseAuth
import com.hasan.dnb.auth.AuthRepository
import com.google.firebase.auth.GoogleAuthProvider as FirebaseGoogleAuthProvider
import com.hasan.dnb.domain.GoogleAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signInWithGoogle(idToken: String): Flow<Result<GoogleAccount>> = flow {
        val credential = FirebaseGoogleAuthProvider.getCredential(idToken, null)
        val authResult = suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener { continuation.resume(it) }
                .addOnFailureListener { continuation.resumeWithException(it) }
        }
        val user = authResult.user ?: throw Exception("Firebase returned no user")
        emit(
            Result.success(
                GoogleAccount(
                    userId = user.uid,
                    idToken = idToken,
                    displayName = user.displayName ?: "",
                    photoUrl = user.photoUrl?.toString(),
                    contactNumber = user.phoneNumber
                )
            )
        )
    }

    override fun checkUserSession(): String {
        return if(firebaseAuth.currentUser != null){
            firebaseAuth.currentUser!!.uid
        }else{
            ""
        }
    }
}
