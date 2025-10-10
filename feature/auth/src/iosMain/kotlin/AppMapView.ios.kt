import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
actual fun AppMapView(detectLocation: Int, getLocation: (lat: Double, long: Double) -> Unit) {
    // For iOS, we'll use a default location for now
    // In a real implementation, you would use iOS location APIs
    val dhaka = LatLng(23.777176, 90.399452)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(dhaka, 14f)
    }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = false // Disabled for iOS as we don't have permission handling yet
        ),
        uiSettings = MapUiSettings(
            myLocationButtonEnabled = false, // Disabled for iOS as we don't have permission handling yet
            zoomControlsEnabled = true
        ),
        onMapClick = { location ->
            // Handle map click if needed
            userLocation = LatLng(location.latitude, location.longitude)
            getLocation.invoke(location.latitude, location.longitude)
        }
    ) {
        // Show marker for user's current location
        userLocation?.let { location ->
            val markerState = rememberMarkerState(position = location)
            Marker(
                state = markerState,
                title = "Selected Location",
                snippet = "${location.latitude}, ${location.longitude}",
            )
        }

        // Fallback marker for Dhaka if location is not available
        if (userLocation == null) {
            val dhaka = LatLng(23.777176, 90.399452)
            val markerState = rememberMarkerState(position = dhaka)
            Marker(
                state = markerState,
                title = "Dhaka",
                snippet = "Default location",
            )
        }
    }
}