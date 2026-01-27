import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@androidx.compose.runtime.Composable
actual fun AppMapView(
    detectLocation: Int,
    getLocation: (lat: Double, long: Double) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text("Map is not supported on Desktop")
    }
}