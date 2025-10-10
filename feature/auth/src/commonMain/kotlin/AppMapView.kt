import androidx.compose.runtime.Composable

@Composable
expect fun AppMapView(detectLocation: Int, getLocation: (lat: Double, long: Double) -> Unit,)