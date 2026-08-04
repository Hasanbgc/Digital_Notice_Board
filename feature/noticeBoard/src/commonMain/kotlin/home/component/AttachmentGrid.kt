package home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import domain.model.AttachmentItem

/**
 * Reusable AttachmentGrid component that renders images, videos, PDFs, and generic files
 * according to the exact layout design specs (1, 2, 3, 4, >4 items).
 */
@Composable
fun AttachmentGrid(
    items: List<AttachmentItem>,
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    onItemClick: (AttachmentItem, Int) -> Unit
) {
    if (items.isEmpty()) return

    // Separate media into visual items (Image/Video) and document/file items (Pdf/Unknown)
    val visualItems = items.filter { it is AttachmentItem.Image || it is AttachmentItem.Video }
    val documentItems = items.filter { it is AttachmentItem.Pdf || it is AttachmentItem.Unknown }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        // Render Visual Attachments Grid
        if (visualItems.isNotEmpty()) {
            VisualAttachmentGrid(
                visualItems = visualItems,
                spacing = spacing,
                onItemClick = { item, index ->
                    val overallIndex = items.indexOf(item).let { if (it >= 0) it else index }
                    onItemClick(item, overallIndex)
                }
            )
        }

        // Render Document Attachments List
        for (doc in documentItems) {
            val docIndex = items.indexOf(doc).let { if (it >= 0) it else 0 }
            when (doc) {
                is AttachmentItem.Pdf -> {
                    PdfAttachment(
                        item = doc,
                        onClick = { onItemClick(doc, docIndex) }
                    )
                }

                is AttachmentItem.Unknown -> {
                    UnknownAttachment(
                        item = doc,
                        onClick = { onItemClick(doc, docIndex) }
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun VisualAttachmentGrid(
    visualItems: List<AttachmentItem>,
    spacing: Dp,
    onItemClick: (AttachmentItem, Int) -> Unit
) {
    when (visualItems.size) {
        1 -> {
            // Single Attachment: Full Width
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                RenderVisualItem(
                    item = visualItems[0],
                    modifier = Modifier.fillMaxSize(),
                    onClick = { onItemClick(visualItems[0], 0) }
                )
            }
        }

        2 -> {
            // Two Attachments: Side by Side (50 / 50)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                RenderVisualItem(
                    item = visualItems[0],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onClick = { onItemClick(visualItems[0], 0) }
                )
                RenderVisualItem(
                    item = visualItems[1],
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    onClick = { onItemClick(visualItems[1], 1) }
                )
            }
        }

        3 -> {
            // Three Attachments: First larger on left, two stacked on right
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                RenderVisualItem(
                    item = visualItems[0],
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxSize(),
                    onClick = { onItemClick(visualItems[0], 0) }
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    RenderVisualItem(
                        item = visualItems[1],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        onClick = { onItemClick(visualItems[1], 1) }
                    )
                    RenderVisualItem(
                        item = visualItems[2],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        onClick = { onItemClick(visualItems[2], 2) }
                    )
                }
            }
        }

        else -> {
            // 4 or >4 Attachments: 2x2 Grid with +N overlay on 4th item
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(spacing)
            ) {
                // Top Row (Item 0 and Item 1)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    RenderVisualItem(
                        item = visualItems[0],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        onClick = { onItemClick(visualItems[0], 0) }
                    )
                    RenderVisualItem(
                        item = visualItems[1],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        onClick = { onItemClick(visualItems[1], 1) }
                    )
                }

                // Bottom Row (Item 2 and Item 3 with optional +N overlay)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    RenderVisualItem(
                        item = visualItems[2],
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        onClick = { onItemClick(visualItems[2], 2) }
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                    ) {
                        RenderVisualItem(
                            item = visualItems[3],
                            modifier = Modifier.fillMaxSize(),
                            onClick = { onItemClick(visualItems[3], 3) }
                        )

                        val extraCount = visualItems.size - 4
                        if (extraCount > 0) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.65f))
                                    .clickable { onItemClick(visualItems[3], 3) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+$extraCount",
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RenderVisualItem(
    item: AttachmentItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    when (item) {
        is AttachmentItem.Image -> {
            ImageAttachment(
                item = item,
                modifier = modifier,
                onClick = onClick
            )
        }

        is AttachmentItem.Video -> {
            VideoAttachment(
                item = item,
                modifier = modifier,
                onClick = onClick
            )
        }

        else -> {
            Spacer(modifier = modifier)
        }
    }
}
