package createNotice

import PickedFile
import rememberImagePickerLauncher
import rememberPdfPickerLauncher
import rememberVideoPickerLauncher
import rememberVideoPlaybackLauncher
import rememberPdfOpenLauncher
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.BGGreen
import presentation.BGGreenIcon
import presentation.BGRed
import presentation.BGRedIcon
import presentation.LightBorder
import presentation.NeutralGray500
import presentation.PrimaryText
import presentation.PrimaryTextAlt2
import presentation.SecondaryGreen
import presentation.TertiaryGreen
import presentation.VerifyTitleColor
import kotlin.random.Random

@Composable
fun NoteBodyScreen(
    state: CreateNoticeScreenState,
    onAction: (CreateNoticeScreenAction) -> Unit
) {
    val maxLength = 100
    val titleCount = state.title.length

    val isPreview = LocalInspectionMode.current
    val onImagesPicked: (List<PickedFile>) -> Unit = { files ->
        onAction(CreateNoticeScreenAction.OnAttachmentsAdded(files.map { it.toAttachment(AttachmentType.IMAGE) }))
    }
    val onVideosPicked: (List<PickedFile>) -> Unit = { files ->
        onAction(CreateNoticeScreenAction.OnAttachmentsAdded(files.map { it.toAttachment(AttachmentType.VIDEO) }))
    }
    val onPdfsPicked: (List<PickedFile>) -> Unit = { files ->
        onAction(CreateNoticeScreenAction.OnAttachmentsAdded(files.map { it.toAttachment(AttachmentType.PDF) }))
    }
    val launchImagePicker = if (isPreview) remember { {} } else rememberImagePickerLauncher(onImagesPicked)
    val launchVideoPicker = if (isPreview) remember { {} } else rememberVideoPickerLauncher(onVideosPicked)
    val launchPdfPicker = if (isPreview) remember { {} } else rememberPdfPickerLauncher(onPdfsPicked)
    val playVideo = if (isPreview) remember { { _: String -> } } else rememberVideoPlaybackLauncher()
    val openPdf = if (isPreview) remember { { _: String -> } } else rememberPdfOpenLauncher()
    var showLocationPicker by remember { mutableStateOf(false) }
    var managingType by remember { mutableStateOf<AttachmentType?>(null) }

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
                    .background(color = getColor(state.selectedCategory?.id).third, shape = RoundedCornerShape(8.dp))
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
                value = state.title,
                onValueChange = { newValue ->
                    if (newValue.length <= maxLength) {
                        onAction(CreateNoticeScreenAction.OnTitleChanged(newValue))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Write Notice Title")
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
                value = state.details,
                onValueChange = { newValue ->
                    onAction(CreateNoticeScreenAction.OnDetailsChanged(newValue))
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Attachments",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText,
                )
            )
            AttachmentGrid(
                attachments = state.attachments,
                onAddImage = launchImagePicker,
                onAddVideo = launchVideoPicker,
                onAddPdf = launchPdfPicker,
                onAddLocation = { showLocationPicker = true },
                onManage = { type ->
                    if (type == AttachmentType.LOCATION) {
                        onAction(CreateNoticeScreenAction.OnManageAttachmentsClicked(type))
                    } else {
                        managingType = type
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showLocationPicker) {
        LocationPickerDialog(
            onLocationSelected = { lat, lon ->
                onAction(
                    CreateNoticeScreenAction.OnAttachmentsAdded(
                        listOf(
                            Attachment(
                                id = generateAttachmentId(),
                                name = "Current Location",
                                type = AttachmentType.LOCATION,
                                uri = "$lat,$lon"
                            )
                        )
                    )
                )
                showLocationPicker = false
            },
            onDismiss = { showLocationPicker = false }
        )
    }

    managingType?.let { type ->
        if (type == AttachmentType.PDF) {
            PdfManagementBottomSheet(
                attachments = state.attachments.filter { it.type == AttachmentType.PDF },
                onAddMore = launchPdfPicker,
                onRemove = { id -> onAction(CreateNoticeScreenAction.OnRemoveAttachment(id)) },
                onOpen = openPdf,
                onDismiss = { managingType = null }
            )
        } else {
            AttachmentManagementBottomSheet(
                type = type,
                attachments = state.attachments.filter { it.type == type },
                onAddMore = if (type == AttachmentType.IMAGE) launchImagePicker else launchVideoPicker,
                onRemove = { id -> onAction(CreateNoticeScreenAction.OnRemoveAttachment(id)) },
                onPlayVideo = playVideo,
                onDismiss = { managingType = null }
            )
        }
    }
}

/**
 * A compact 2x2 grid where each cell is a "stateful" attachment card: it acts as the
 * picker in its empty state and becomes the preview/management entry point once the
 * user has selected at least one attachment of that type. This replaces the previous
 * separate picker row + attachments preview list.
 */
@Composable
private fun AttachmentGrid(
    attachments: List<Attachment>,
    onAddImage: () -> Unit,
    onAddVideo: () -> Unit,
    onAddPdf: () -> Unit,
    onAddLocation: () -> Unit,
    onManage: (AttachmentType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AttachmentCard(
                type = AttachmentType.IMAGE,
                attachments = attachments.filter { it.type == AttachmentType.IMAGE },
                onAddClick = onAddImage,
                onManageClick = { onManage(AttachmentType.IMAGE) },
                modifier = Modifier.weight(1f)
            )
            AttachmentCard(
                type = AttachmentType.VIDEO,
                attachments = attachments.filter { it.type == AttachmentType.VIDEO },
                onAddClick = onAddVideo,
                onManageClick = { onManage(AttachmentType.VIDEO) },
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AttachmentCard(
                type = AttachmentType.PDF,
                attachments = attachments.filter { it.type == AttachmentType.PDF },
                onAddClick = onAddPdf,
                onManageClick = { onManage(AttachmentType.PDF) },
                modifier = Modifier.weight(1f)
            )
            AttachmentCard(
                type = AttachmentType.LOCATION,
                attachments = attachments.filter { it.type == AttachmentType.LOCATION },
                onAddClick = onAddLocation,
                onManageClick = { onManage(AttachmentType.LOCATION) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun PickedFile.toAttachment(type: AttachmentType) = Attachment(
    id = generateAttachmentId(),
    name = name,
    type = type,
    uri = uri
)

fun generateAttachmentId(): String = Random.nextLong().toString()

/**
 * Square, stateful attachment card. Empty: acts as an "Add attachment" action.
 * Filled: becomes the preview and the entry point into management (view/replace/remove).
 */
@Composable
fun AttachmentCard(
    type: AttachmentType,
    attachments: List<Attachment>,
    onAddClick: () -> Unit,
    onManageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFilled = attachments.isNotEmpty()

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isFilled) Color.White else Color(0xFFF5F5F5))
            .border(1.dp, LightBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = if (isFilled) onManageClick else onAddClick)
    ) {
        AnimatedContent(
            targetState = isFilled,
            modifier = Modifier.fillMaxSize(),
            transitionSpec = {
                fadeIn(tween(200)).togetherWith(fadeOut(tween(150)))
            },
            label = "attachment-card-${type.name}"
        ) { filled ->
            if (filled) {
                AttachmentFilledContent(type = type, attachments = attachments)
            } else {
                AttachmentEmptyContent(type = type)
            }
        }
    }
}

@Composable
private fun AttachmentEmptyContent(type: AttachmentType) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(0xFFEFEFEF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = type.icon,
                contentDescription = type.label,
                tint = NeutralGray500,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = type.label,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            )
        )
    }
}

@Composable
private fun AttachmentFilledContent(type: AttachmentType, attachments: List<Attachment>) {
    // AnimatedContent keeps composing the outgoing "filled" branch during its exit
    // fade using the latest `attachments`, so this can transiently be empty right
    // after the last item of this type is removed — render nothing for that frame
    // instead of crashing.
    val first = attachments.firstOrNull() ?: return
    val count = attachments.size

    when (type) {
        AttachmentType.IMAGE -> ThumbnailFilledContent(label = "Image", count = count) {
            AsyncImage(
                model = first.uri,
                contentDescription = "Image thumbnail",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        AttachmentType.VIDEO -> ThumbnailFilledContent(label = "Video", count = count) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play video",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        AttachmentType.PDF -> IconFilledContent(
            icon = Icons.Default.PictureAsPdf,
            iconTint = Color.Red,
            iconBg = BGRed,
            title = first.name,
            subtitle = "PDF ($count)"
        )

        AttachmentType.LOCATION -> IconFilledContent(
            icon = Icons.Default.LocationOn,
            iconTint = TertiaryGreen,
            iconBg = BGGreen,
            title = "Selected",
            subtitle = null
        )
    }
}

@Composable
private fun ThumbnailFilledContent(
    label: String,
    count: Int,
    thumbnail: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        thumbnail()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.65f))
                    )
                )
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                text = "$label ($count)",
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun IconFilledContent(
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    title: String,
    subtitle: String?
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Medium,
                color = PrimaryText
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        if (subtitle != null) {
            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = NeutralGray500
                )
            )
        }
    }
}

private val AttachmentType.icon: ImageVector
    get() = when (this) {
        AttachmentType.IMAGE -> Icons.Default.Image
        AttachmentType.VIDEO -> Icons.Default.VideoLibrary
        AttachmentType.PDF -> Icons.Default.PictureAsPdf
        AttachmentType.LOCATION -> Icons.Default.LocationOn
    }

private val AttachmentType.label: String
    get() = when (this) {
        AttachmentType.IMAGE -> "Image"
        AttachmentType.VIDEO -> "Video"
        AttachmentType.PDF -> "PDF"
        AttachmentType.LOCATION -> "Location"
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
                    Attachment("5", "Image2.jpg", AttachmentType.IMAGE, "https://picsum.photos/400/301"),
                    Attachment("3", "Video.mp4", AttachmentType.VIDEO),
                    Attachment("2", "Document.pdf", AttachmentType.PDF),
                    Attachment("4", "Current Location", AttachmentType.LOCATION)
                )
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
fun NoteBodyScreenEmptyAttachmentsPreview() {
    MaterialTheme {
        NoteBodyScreen(
            state = CreateNoticeScreenState(
                selectedCategory = categories[0],
                attachments = emptyList()
            ),
            onAction = {}
        )
    }
}
