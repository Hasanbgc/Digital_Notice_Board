package createNotice

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import presentation.LightBorder
import presentation.NeutralGray500
import presentation.PrimaryText
import presentation.TertiaryGreen

/**
 * Bottom sheet for managing already-selected image or video attachments: view thumbnails,
 * play a video, remove individual items, or add more of the same type.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentManagementBottomSheet(
    type: AttachmentType,
    attachments: List<Attachment>,
    onAddMore: () -> Unit,
    onRemove: (id: String) -> Unit,
    onPlayVideo: (uri: String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val title = if (type == AttachmentType.IMAGE) "Images" else "Videos"

    LaunchedEffect(attachments) {
        if (attachments.isEmpty()) onDismiss()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$title (${attachments.size})",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = NeutralGray500
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 340.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(attachments, key = { it.id }) { attachment ->
                    ManagedAttachmentItem(
                        attachment = attachment,
                        type = type,
                        onRemove = { onRemove(attachment.id) },
                        onPlay = { attachment.uri?.let(onPlayVideo) }
                    )
                }
                item {
                    AddMoreTile(onClick = onAddMore)
                }
            }
        }
    }
}

@Composable
private fun ManagedAttachmentItem(
    attachment: Attachment,
    type: AttachmentType,
    onRemove: () -> Unit,
    onPlay: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .then(if (type == AttachmentType.VIDEO) Modifier.clickable(onClick = onPlay) else Modifier)
    ) {
        when (type) {
            AttachmentType.IMAGE -> AsyncImage(
                model = attachment.uri,
                contentDescription = attachment.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            AttachmentType.VIDEO -> Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play video",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            else -> Unit
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.TopEnd)
                .size(20.dp)
                .background(Color.Black.copy(alpha = 0.55f), CircleShape)
                .padding(2.dp)
                .clip(CircleShape)
                .clickable(onClick = onRemove),
            tint = Color.White
        )
    }
}

@Composable
private fun AddMoreTile(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .border(1.dp, LightBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add more",
                tint = TertiaryGreen,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Add more",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Medium,
                    color = TertiaryGreen
                )
            )
        }
    }
}
