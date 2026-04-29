package createNotice

import AppMapView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.BGGreen
import presentation.BGGreenIcon
import presentation.BGRed
import presentation.BGRedIcon
import presentation.NeutralGray500
import presentation.PrimaryText
import presentation.PrimaryTextAlt2
import presentation.SecondaryGreen
import presentation.TertiaryGreen
import presentation.VerifyTitleColor

@Composable
fun NoteBodyScreen(
    state: CreateNoticeScreenState,
    onAction: (CreateNoticeScreenAction) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    val maxLength = 100
    val titleCount = title.length

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState(0)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Selected Category",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = SecondaryGreen,
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, getColor(state.selectedCategory?.id).first),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = getColor(state.selectedCategory?.id).third)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .background(
                            getColor(state.selectedCategory?.id).second,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = state.selectedCategory?.icon!!,
                        contentDescription = state.selectedCategory.title,
                        modifier = Modifier.size(20.dp),
                        tint = getColor(state.selectedCategory.id).first
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f).padding(4.dp)) {
                    Text(
                        text = state.selectedCategory?.title!!,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = getColor(state.selectedCategory.id).first,
                        )
                    )
                    Text(
                        text = state.selectedCategory.description,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Normal,
                            color = PrimaryTextAlt2
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .clickable {
                            onAction(CreateNoticeScreenAction.OnCategoryChangeClicked)
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "Change",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = getColor(state.selectedCategory?.id).first,
                        )
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Notice Title",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText,
                    )
                )
                Text(
                    text = "$titleCount/100",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Normal,
                        color = NeutralGray500
                    )
                )
            }
            TextField(
                value = title,
                onValueChange = { newValue ->
                    if (newValue.length <= maxLength) {
                        title = newValue
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Enter Notice Title")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = VerifyTitleColor,
                    focusedPlaceholderColor = NeutralGray500,
                    unfocusedPlaceholderColor = NeutralGray500
                )

            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Details",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText,
                )
            )
            TextField(
                value = details,
                onValueChange = { newValue ->
                    details = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp, max = 200.dp),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Write details here...")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = VerifyTitleColor,
                    focusedPlaceholderColor = NeutralGray500,
                    unfocusedPlaceholderColor = NeutralGray500
                )
            )
        }

        if (state.attachments.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Attachments Preview",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText,
                    )
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.attachments.forEach { attachment ->
                        AttachmentPreviewItem(
                            attachment = attachment,
                            onRemove = { onAction(CreateNoticeScreenAction.OnRemoveAttachment(attachment.id)) }
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Add Attachments",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText,
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AttachmentItem(
                    icon = Icons.Default.Image,
                    label = "Image",
                    onClick = { onAction(CreateNoticeScreenAction.OnAddImageClicked) }
                )
                AttachmentItem(
                    icon = Icons.Default.VideoLibrary,
                    label = "Video",
                    onClick = { onAction(CreateNoticeScreenAction.OnAddVideoClicked) }
                )
                AttachmentItem(
                    icon = Icons.Default.PictureAsPdf,
                    label = "PDF",
                    onClick = { onAction(CreateNoticeScreenAction.OnAddPdfClicked) }
                )
                AttachmentItem(
                    icon = Icons.Default.LocationOn,
                    label = "Location",
                    onClick = { onAction(CreateNoticeScreenAction.OnAddLocationClicked) }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AttachmentPreviewItem(
    attachment: Attachment,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                shape = RoundedCornerShape(8.dp)
            )
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            when (attachment.type) {
                AttachmentType.IMAGE -> {
                    AsyncImage(
                        model = attachment.uri,
                        contentDescription = "Image preview",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f),
                        contentScale = ContentScale.Crop
                    )
                }
                AttachmentType.VIDEO -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play video",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                AttachmentType.LOCATION -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        AppMapView(
                            detectLocation = 0,
                            getLocation = { lat: Double, lon: Double -> }
                        )
                        // Overlay to prevent interaction in preview
                        Box(modifier = Modifier.fillMaxSize().clickable(enabled = false) {})
                    }
                }
                AttachmentType.PDF -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = attachment.name,
                            modifier = Modifier.weight(1f),
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryText
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            if (attachment.type != AttachmentType.PDF) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val icon = when (attachment.type) {
                        AttachmentType.IMAGE -> Icons.Default.Image
                        AttachmentType.VIDEO -> Icons.Default.VideoLibrary
                        AttachmentType.LOCATION -> Icons.Default.LocationOn
                        else -> Icons.Default.Image
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = TertiaryGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = attachment.name,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryText
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd)
                .size(24.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
                .clickable { onRemove() },
            tint = Color.White
        )
    }
}

@Composable
fun AttachmentItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFE0E0E0), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = NeutralGray500,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            )
        )
    }
}

private fun getColor(id: Int?): Triple<Color, Color, Color> {
    return when (id) {
        1 -> Triple(Color.Red, BGRedIcon, BGRed)
        2 -> Triple(TertiaryGreen, BGGreenIcon, BGGreen)
        else -> {
            Triple(TertiaryGreen, BGGreenIcon, BGGreen)
        }
    }
}

@Preview
@Composable
fun NoteBodyScreenPreview() {
    MaterialTheme {
        NoteBodyScreen(
            state = CreateNoticeScreenState(
                selectedCategory = categories[0],
                attachments = listOf(
                    Attachment("1", "Image.jpg", AttachmentType.IMAGE, "https://picsum.photos/400/300"),
                    Attachment("2", "Document.pdf", AttachmentType.PDF),
                    Attachment("3", "Video.mp4", AttachmentType.VIDEO),
                    Attachment("4", "Current Location", AttachmentType.LOCATION)
                )
            ),
            onAction = {}
        )
    }
}
