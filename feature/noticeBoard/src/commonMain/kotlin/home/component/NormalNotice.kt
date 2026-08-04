package home.component

import KottieAnimation
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.chat
import digita_notice_board.feature.noticeboard.generated.resources.flag
import digita_notice_board.feature.noticeboard.generated.resources.flaged
import digita_notice_board.feature.noticeboard.generated.resources.heart
import digita_notice_board.feature.noticeboard.generated.resources.share
import domain.model.toAttachmentItem
import home.HomeScreenAction
import home.Like
import home.Poster
import home.getAnimation
import org.jetbrains.compose.resources.painterResource
import presentation.ButtonCardGradiant
import presentation.GradientGreen
import presentation.PrimaryText
import presentation.PrimaryTextAlt2
import presentation.TertiaryGreen

@Composable
fun NormalNotice(
    modifier: Modifier = Modifier,
    poster: Poster.Normal,
    onAction: (HomeScreenAction) -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showSeeMore by remember { mutableStateOf(false) }
    var toggleLiked by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(durationMillis = 300))
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ProfileImageWithPlaceholder(
                            modifier = Modifier.size(46.dp),
                            imageUrl = poster.profile.imageUrl,
                            institutionName = poster.profile.institution,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(
                            modifier = Modifier.wrapContentSize(
                                align = Alignment.TopStart,
                                unbounded = false
                            )
                        ) {
                            Text(
                                text = poster.profile.name + poster.id,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                color = PrimaryText,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = poster.profile.designation,
                                fontSize = 12.sp,
                                color = PrimaryTextAlt2
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.background(
                                brush = GradientGreen,
                                shape = RoundedCornerShape(8.dp)
                            ).padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "notification",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = poster.category.ifBlank { "General" },
                                fontSize = 12.sp,
                                color = Color.White,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "location",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = poster.title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = poster.description,
                    fontSize = 12.sp,
                    color = PrimaryTextAlt2,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { isExpanded = !isExpanded },
                    onTextLayout = {
                        if (it.hasVisualOverflow) {
                            showSeeMore = true
                        }
                    }
                )
                if (showSeeMore && !isExpanded) {
                    Text(
                        text = "see more",
                        color = Color.Blue,
                        modifier = Modifier.clickable { isExpanded = true }
                    )
                }

                val mediaItemsToDisplay = remember(poster.mediaItems, poster.imageUrlList, poster.attachments) {
                    if (poster.mediaItems.isNotEmpty()) {
                        poster.mediaItems
                    } else {
                        val images = poster.imageUrlList.mapIndexed { idx, url -> url.toAttachmentItem(idx) }
                        val other = poster.attachments.mapIndexed { idx, att -> att.toAttachmentItem(images.size + idx) }
                        images + other
                    }
                }

                if (mediaItemsToDisplay.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    AttachmentGrid(
                        items = mediaItemsToDisplay,
                        onItemClick = { _, index ->
                            onAction(HomeScreenAction.OnAttachmentClicked(mediaItemsToDisplay, index))
                        }
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(brush = ButtonCardGradiant)
                    .padding(16.dp)
            )
            {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable(onClick = {
                            toggleLiked = !toggleLiked
                            onAction(HomeScreenAction.OnLikeClicked(poster.id, toggleLiked))
                        }
                        ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        when (poster.liked) {
                            Like.IDLE -> {
                                Icon(
                                    painter = painterResource(Res.drawable.heart),
                                    contentDescription = "like",
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.Black
                                )
                            }

                            Like.LIKED -> {
                                val animationLike = getAnimation("files/love.json", 1)
                                KottieAnimation(
                                    composition = animationLike.first,
                                    modifier = Modifier.size(14.dp),
                                    progress = { animationLike.second.progress }
                                )
                            }

                            Like.UNLIKED -> {
                                Icon(
                                    painter = painterResource(Res.drawable.heart),
                                    contentDescription = "like",
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.Black
                                )
                            }

                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text ="${poster.likeCount}",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))

                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable { onAction(HomeScreenAction.OnCommentClicked(poster.id)) },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.chat),
                            contentDescription = "comments",
                            modifier = Modifier.size(15.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${poster.commentCount}",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Card(
                    modifier = Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.share),
                            contentDescription = "shared",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "123",
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(if (poster.isSaved) Res.drawable.flaged else Res.drawable.flag),
                    contentDescription = "right_arrow",
                    tint = if (poster.isSaved) TertiaryGreen else Color.Black,
                    modifier = Modifier.size(20.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onAction(HomeScreenAction.OnSavedClicked(poster))
                        },
                )
            }
        }
    }
}