import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.hasan.dnb.domain.GoogleAccount

actual class GoogleAuthUiProvider(
    val context: Context,
    val credentialManager: CredentialManager
) {
    actual suspend fun signIn(): GoogleAccount? = try {
        val credential = credentialManager.getCredential(
            context,
            getCredentialRequest()
        ).credential
        handleSignIn(credential)

    }catch (e: Exception){
        null
    }

    private fun handleSignIn(credential: Credential): GoogleAccount?{
        return  when{
            credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL  -> {
                try{
                    val googleCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    GoogleAccount(
                        userId = googleCredential.id,
                        idToken = googleCredential.idToken,
                        displayName = googleCredential.displayName ?: "",
                        photoUrl = googleCredential.profilePictureUri?.toString(),
                        contactNumber = googleCredential.phoneNumber
                    )
                }catch (e: GoogleIdTokenParsingException){
                    null
                }
            }
            else ->{
                null
            }
        }
    }
    private fun getCredentialRequest(): GetCredentialRequest{
        return GetCredentialRequest
            .Builder()
            .addCredentialOption(googleIdOption())
            .build()
    }
    private fun googleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption
            .Builder()
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .setServerClientId("435662661341-hkpu339l4qrpl9a0f64m0ogh2gpksrru.apps.googleusercontent.com")
            .build()
    }
}