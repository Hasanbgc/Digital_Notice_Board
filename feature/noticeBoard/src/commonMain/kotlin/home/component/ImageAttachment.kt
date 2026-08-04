package home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import domain.model.AttachmentItem

@Composable
fun ImageAttachment(
    item: AttachmentItem.Image,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 8.dp,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .semantics { contentDescription = "Image attachment ${item.url}" }
            .clickable { onClick() },
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f))
    ) {
        AsyncImage(
            model = item.url,
            contentDescription = "Post image attachment",
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )
    }
}
