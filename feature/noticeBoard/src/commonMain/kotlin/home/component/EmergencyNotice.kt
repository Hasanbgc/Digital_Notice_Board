package home.component

import KottieAnimation
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import home.Poster
import home.Type
import home.getIcon
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
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
fun EmergencyNotice(
    poster: Poster.Emergency,
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit
) {
    var animation by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        animation = Res.readBytes(getIcon(poster)).decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = KottieConstants.IterateForever
    )

    Row(
        modifier = modifier then Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 500))
            .NeonEffect()
            .background(brush = EmergencyGradient, shape = RoundedCornerShape(8.dp))
            .clickable {
                poster.isExpanded = !poster.isExpanded
            }
            .padding(horizontal = 10.dp, vertical = 14.dp),
    )
    {

        KottieAnimation(
            composition = composition,
            modifier = Modifier.size(30.dp)
                .clip(shape = CircleShape)
                .background(color = EmergencyIconBG)
                .padding(4.dp),
            progress = { animationState.progress }
        )

        Column(modifier = Modifier.padding(start = 8.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(
                    text = poster.title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
                Card(
                    Modifier.wrapContentSize(),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (poster.type) {
                            Type.HIGH -> HighBackground
                            Type.MEDIUM -> MediumBackground
                            Type.NORMAL -> NormalBackground
                            else -> {
                                EmergencyIconBG
                            }
                        }
                    )
                )
                {
                    Text(
                        text = poster.type.name,
                        fontSize = 12.sp,
                        color = when (poster.type) {
                            Type.HIGH -> HighText
                            Type.MEDIUM -> MediumText
                            Type.NORMAL -> NormalText
                            else -> {
                                ErrorRed
                            }
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    )
                }

            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = poster.description,
                fontSize = 12.sp,
                color = PrimaryTextAlt2,
                maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.weight(0.7f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier.size(12.dp),
                        tint = PrimaryTextAlt2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${poster.distance}, ${poster.location}",
                        fontSize = 12.sp,
                        color = PrimaryTextAlt2,
                        maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = "timer",
                        modifier = Modifier.size(12.dp),
                        tint = PrimaryTextAlt2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = poster.time,
                        fontSize = 12.sp,
                        color = PrimaryTextAlt2
                    )
                }
            }
        }
    }
}