package home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.document
import org.jetbrains.compose.resources.painterResource
import presentation.BorderGray
import presentation.FileCardGradiant
import presentation.PrimaryBlue
import presentation.ShareButtonGradiant

@Composable
fun AttachmentCard(
    attachment: String
) {
    Card(
        border = BorderStroke(0.5.dp, BorderGray),
        modifier = Modifier.fillMaxWidth(),
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
                painter = painterResource(Res.drawable.document),
                contentDescription = "File",
                modifier = Modifier
                    .background(brush = ShareButtonGradiant, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = attachment,
                modifier = Modifier.padding()
            )

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.FileDownload,
                contentDescription = "Close",
                tint = PrimaryBlue
            )
        }
    }
}