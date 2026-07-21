package createNotice

import AppMapView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import presentation.NeutralGray500
import presentation.PrimaryText

@Composable
fun LocationPickerDialog(
    onLocationSelected: (lat: Double, lon: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Pick a Location",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tap on the map to choose where this notice applies",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = NeutralGray500
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    AppMapView(
                        detectLocation = 1,
                        getLocation = { lat, lon -> selectedLocation = lat to lon }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            selectedLocation?.let { (lat, lon) -> onLocationSelected(lat, lon) }
                        },
                        enabled = selectedLocation != null
                    ) {
                        Text("Use this location")
                    }
                }
            }
        }
    }
}
