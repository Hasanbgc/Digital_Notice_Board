package data

import GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.hasan.dnb.auth.AuthRepository
import com.hasan.dnb.domain.GoogleAccount
import com.hasan.dnb.domain.UserSession
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.google.firebase.auth.GoogleAuthProvider as FirebaseGoogleAuthProvider

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

    override suspend fun signOutFromGoogle() {
        firebaseAuth.signOut()
    }

    override fun observeAuthState(): Flow<UserSession> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val currentUser = firebaseAuth.currentUser
            if (currentUser == null) {
                trySend(UserSession(
                    uid = "",
                    displayName = "",
                    photoUrl = null,
                    contactNumber = null
                ))
            } else {
                currentUser.reload()
                    .addOnSuccessListener { trySend(UserSession(
                        uid = currentUser.uid,
                        displayName = currentUser.displayName,
                        photoUrl = currentUser.photoUrl?.toString(),
                        contactNumber = currentUser.phoneNumber
                    )) }
                    .addOnFailureListener {
                        firebaseAuth.signOut()
                        trySend(UserSession(
                            uid = "",
                            displayName = "",
                            photoUrl = null,
                            contactNumber = null
                        ))
                    }
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

}
