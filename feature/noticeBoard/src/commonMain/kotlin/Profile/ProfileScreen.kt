package Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel,
    onBackPressed: () -> Unit
) {
    ProfileScreen()
}

@Composable
fun ProfileScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.Red),
        contentAlignment = Alignment.Center,
    ){
        Text("I'm under the everything")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .blur(24.dp)
                .background(Color.White.copy(alpha = 0.45f)),
            contentAlignment = Alignment.Center
        ){

        }
        Text("Hello profile")

    }

}