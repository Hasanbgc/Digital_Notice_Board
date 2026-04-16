package home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import home.HomeScreenAction
import home.HomeScreenState
import presentation.EmergenceyAlertRedBG
import presentation.ErrorRed
import presentation.PrimaryTextAlt2
import presentation.ViolateGradiant

@Composable
fun HeaderSection(
    state: HomeScreenState,
    onAction: (HomeScreenAction) -> Unit
) {
    val searchToggle by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column(
            modifier = Modifier.wrapContentHeight().padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(
                text = "Notice Board",
                modifier = Modifier
                    .align(Alignment.Start),
                style = TextStyle(
                    brush = ViolateGradiant,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )

            )
            Text(
                text = "Stay updated with important notices",
                modifier = Modifier
                    .align(Alignment.Start),
                color = PrimaryTextAlt2,
                fontSize = 12.sp
            )

        }
        Row(
            modifier = Modifier.wrapContentHeight().padding(8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*RoundGradientButton(
                modifier = Modifier.wrapContentSize(),
                text = "Search",
                icon = Icons.Default.Search,
                iconTint = Color.White,
                gradientColors = GradientGreen,
                shape = RoundedCornerShape(30.dp),
                onClick = { searchToggle != searchToggle }
            )*/

            Spacer(Modifier.width(4.dp))
            AnimatedVisibility(state.emergencyAlertClosed) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = ErrorRed,
                            contentColor = Color.White
                        ) {
                            Text(state.emergencyNotice.size.toString())
                        }
                    }
                ) {
                    RoundGradientButton(
                        modifier = Modifier.wrapContentSize(),
                        text = "notification",
                        icon = Icons.Outlined.Notifications,
                        iconTint = Color.White,
                        gradientColors = SolidColor(EmergenceyAlertRedBG),
                        shape = RoundedCornerShape(30.dp),
                        onClick = { onAction(HomeScreenAction.OnNotificationClicked) }
                    )
                }

            }
        }
    }
}