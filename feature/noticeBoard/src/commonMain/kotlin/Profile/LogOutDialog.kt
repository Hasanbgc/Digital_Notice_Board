package Profile

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LogOutDialog(
    onAction: (ProfileScreenAction) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onAction(ProfileScreenAction.OnLogoutCancelled)},
        title = {
            Text("Logout")
        },
        text = {
            Text("Are you sure you want to log out?")
        },
        confirmButton = {
            TextButton(onClick = { onAction(ProfileScreenAction.OnLogoutConfirmed)}) {
                Text("Logout")
            }
        },
        dismissButton = {
            TextButton(onClick = { onAction(ProfileScreenAction.OnLogoutCancelled) }) {
                Text("Cancel")
            }
        }
    )
}

@Composable
@Preview
fun LogOutDialogPreview(){
    LogOutDialog(
        onAction = {},
    )
}