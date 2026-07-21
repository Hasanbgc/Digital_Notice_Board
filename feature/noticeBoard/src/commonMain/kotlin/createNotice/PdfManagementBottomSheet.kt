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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PictureAsPdf
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import presentation.BGRed
import presentation.LightBorder
import presentation.NeutralGray500
import presentation.PrimaryText
import presentation.TertiaryGreen

/**
 * Bottom sheet for managing already-selected PDF attachments: open a document in the
 * platform viewer, remove individual files, or add more PDFs.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfManagementBottomSheet(
    attachments: List<Attachment>,
    onAddMore: () -> Unit,
    onRemove: (id: String) -> Unit,
    onOpen: (uri: String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
                    text = "PDF Documents (${attachments.size})",
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
            Spacer(modifier = Modifier.height(4.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 340.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(attachments, key = { it.id }) { attachment ->
                    PdfRow(
                        attachment = attachment,
                        onOpen = { attachment.uri?.let(onOpen) },
                        onRemove = { onRemove(attachment.id) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, LightBorder, RoundedCornerShape(12.dp))
                    .clickable(onClick = onAddMore)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add more",
                    tint = TertiaryGreen,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Add more PDFs",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.Medium,
                        color = TertiaryGreen
                    )
                )
            }
        }
    }
}

@Composable
private fun PdfRow(
    attachment: Attachment,
    onOpen: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .clickable(onClick = onOpen)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(BGRed, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.PictureAsPdf,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
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
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Remove",
            tint = NeutralGray500,
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .clickable(onClick = onRemove)
        )
    }
}
