import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState

@Composable
actual fun AppMapView(detectLocation: Int, getLocation: (lat: Double, long: Double) -> Unit) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var cameraPosition by remember { mutableStateOf(CameraPosition.fromLatLngZoom(LatLng(23.777176, 90.399452), 14f)) }
    var showPermissionRationale by remember { mutableStateOf(false) }
    var showEducationalDialog by remember { mutableStateOf(true) } // Show educational dialog first
    var locationPermissionGranted by remember { mutableStateOf(false) } // Track permission status
    
    val cameraPositionState = rememberCameraPositionState {
        position = cameraPosition
    }

    // Permission launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        
        locationPermissionGranted = fineLocationGranted || coarseLocationGranted
        
        if (locationPermissionGranted) {
            // Permission granted, get current location
            getCurrentLocation(context) { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    cameraPosition = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                    cameraPositionState.position = cameraPosition
                    getLocation.invoke(it.latitude, it.longitude)
                }
            }
        } else {
            // Permission denied, check if user selected "Don't ask again"
            showPermissionRationale = !ActivityCompat.shouldShowRequestPermissionRationale(
                context as android.app.Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    // Settings launcher for opening app settings
    val settingsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        // After returning from settings, check permission again
        if (checkLocationPermission(context)) {
            locationPermissionGranted = true
            getCurrentLocation(context) { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    cameraPosition = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                    cameraPositionState.position = cameraPosition
                    getLocation.invoke(it.latitude, it.longitude)
                }
            }
        }
    }

    // Educational dialog explaining why we need location permission
    if (showEducationalDialog) {
        AlertDialog(
            onDismissRequest = { showEducationalDialog = false },
            title = {
                Text("Location Permission Required")
            },
            text = {
                Text("We need access to your location to:\n\n" +
                        "• Show your position on the map\n" +
                        "• Provide location-based notices relevant to your area\n" +
                        "• Help notice posters target their announcements to nearby users\n\n" +
                        "Your location data is only used within the app and is not shared with third parties.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showEducationalDialog = false
                        // Now request the actual permission
                        locationPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEducationalDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    // Trigger location detection when detectLocation parameter changes
    LaunchedEffect(detectLocation) {
        if (detectLocation > 0 && locationPermissionGranted) {
            getCurrentLocation(context) { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    cameraPosition = CameraPosition.fromLatLngZoom(LatLng(it.latitude, it.longitude), 15f)
                    cameraPositionState.position = cameraPosition
                    getLocation.invoke(it.latitude, it.longitude)
                }
            }
        }
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            // Once camera stops moving, update marker to new camera target
            val currentTarget = cameraPositionState.position.target
            userLocation = currentTarget
        }
    }

    // Show permission rationale or settings button if needed
    if (showPermissionRationale) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Location permission is required to show your current location on the map.")
            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context.packageName, null)
                    intent.data = uri
                    settingsLauncher.launch(intent)
                }
            ) {
                Text("Open Settings")
            }
        }
    } else if (!showEducationalDialog) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = locationPermissionGranted // Only enable if we have permission
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = locationPermissionGranted, // Only enable if we have permission
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
                val markerState = rememberUpdatedMarkerState(position = location)
                Marker(
                    state = markerState,
                    title = "Your Location",
                    snippet = "${location.latitude}, ${location.longitude}",
                )
            }

            // Fallback marker for Dhaka if location is not available
            if (userLocation == null) {
                val dhaka = LatLng(23.777176, 90.399452)
                val markerState = rememberUpdatedMarkerState(position = dhaka)
                Marker(
                    state = markerState,
                    title = "Dhaka",
                    snippet = "Default location",
                )
            }
        }
    }
}

private fun checkLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

private fun getCurrentLocation(context: Context, callback: (Location?) -> Unit) {
    if (!checkLocationPermission(context)) {
        callback(null)
        return
    }

    // Check if location services are enabled
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && 
        !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
        callback(null)
        return
    }

    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    try {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                callback(location)
            }
            .addOnFailureListener {
                callback(null)
            }
    } catch (e: Exception) {
        callback(null)
    }
}