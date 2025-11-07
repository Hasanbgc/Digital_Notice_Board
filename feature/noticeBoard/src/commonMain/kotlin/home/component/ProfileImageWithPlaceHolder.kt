package home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun ProfileImageWithPlaceholder(
    imageUrl: String?,
    institutionName: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    val initials = remember(institutionName) {
        institutionName
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .take(2) // take first two words
            .joinToString("") { it.first().uppercase() } // take first letter of each
            .take(2) // limit to two chars max
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                onError = { /* show initials if image fails */ }
            )
        } else {
            Text(
                text = initials,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
