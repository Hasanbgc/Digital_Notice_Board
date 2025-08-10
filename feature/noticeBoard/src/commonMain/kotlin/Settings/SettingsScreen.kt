package Settings

import Settings.SettingsViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel,
    onBackPressed: () -> Unit
){
    //val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsScreen(
       // state = state,
        onBackPressed = onBackPressed
    )
}
@Composable
fun SettingsScreen(
    //state:SettingsState,
    onBackPressed: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Settings Screen")

    }
}