package home.component

import KottieAnimation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import digita_notice_board.feature.noticeboard.generated.resources.Res
import home.HomeScreenAction
import home.Poster
import home.Type
import home.getIcon
import kotlinx.coroutines.delay
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import presentation.BorderGray
import presentation.EmergencyGradient
import presentation.EmergencyIconBG
import presentation.ErrorRed
import presentation.HighBackground
import presentation.HighText
import presentation.MediumBackground
import presentation.MediumText
import presentation.NeonEffect
import presentation.NormalBackground
import presentation.NormalText
import presentation.PrimaryTextAlt2
import utils.KottieConstants

@Composable
fun EmergencyCarousel(
    notices: List<Poster.Emergency>,
    modifier: Modifier = Modifier,
    onAction: (HomeScreenAction) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5_000)
            if (!pagerState.isScrollInProgress) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            EmergencyCarouselItem(
                notice = notices[page % notices.size],
                modifier = Modifier.padding(top = 8.dp)
            )
            /*EmergencyNotice(
                poster = notices[page % notices.size],
                onNavigateToDetail = {}
            )*/
        }

        // Page indicator dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(notices.size) { index ->
                val isSelected = (pagerState.currentPage % notices.size) == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (isSelected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) ErrorRed else BorderGray)
                )
            }
        }
    }

}

@Composable
fun EmergencyCarouselItem(
    notice: Poster.Emergency,
    modifier: Modifier = Modifier
) {
    var animation by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        animation = Res.readBytes(getIcon(notice)).decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = KottieConstants.IterateForever
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .NeonEffect()
            .background(brush = EmergencyGradient, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        KottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(EmergencyIconBG)
                .padding(5.dp),
            progress = { animationState.progress }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notice.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(11.dp),
                    tint = PrimaryTextAlt2
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = notice.location,
                    fontSize = 11.sp,
                    color = PrimaryTextAlt2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = null,
                    modifier = Modifier.size(11.dp),
                    tint = PrimaryTextAlt2
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = notice.time,
                    fontSize = 11.sp,
                    color = PrimaryTextAlt2
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (notice.type) {
                    Type.HIGH -> HighBackground
                    Type.MEDIUM -> MediumBackground
                    Type.NORMAL -> NormalBackground
                    else -> EmergencyIconBG
                }
            )
        ) {
            Text(
                text = notice.type.name,
                fontSize = 11.sp,
                color = when (notice.type) {
                    Type.HIGH -> HighText
                    Type.MEDIUM -> MediumText
                    Type.NORMAL -> NormalText
                    else -> ErrorRed
                },
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
            )
        }
    }
}