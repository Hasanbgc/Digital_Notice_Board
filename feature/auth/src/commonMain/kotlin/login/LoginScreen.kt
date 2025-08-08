package login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {

    LoginScreen()

}

@Composable
fun LoginScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Login Screen",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}