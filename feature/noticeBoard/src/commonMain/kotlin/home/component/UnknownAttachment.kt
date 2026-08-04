package home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.AttachmentItem
import presentation.BorderGray
import presentation.FileCardGradiant
import presentation.PrimaryBlue
import presentation.ShareButtonGradiant

@Composable
fun UnknownAttachment(
    item: AttachmentItem.Unknown,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        border = BorderStroke(0.5.dp, BorderGray),
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Attachment ${item.fileName}" }
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = FileCardGradiant)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.InsertDriveFile,
                contentDescription = "Attachment File",
                modifier = Modifier
                    .background(brush = ShareButtonGradiant, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.fileName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "File Attachment",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Icon(
                imageVector = Icons.Outlined.FileDownload,
                contentDescription = "Open file",
                tint = PrimaryBlue,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
