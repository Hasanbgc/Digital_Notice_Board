package registration.component

import AccentGreen
import NeutralGray500
import SignInTextGreen
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedProgressBar(steps: List<String>, currentStep: Int,trackColor: Color,progressColor: Color) {
    val totalSteps = steps.size
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        var center = remember { mutableStateListOf<Float>()}

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentPadding = PaddingValues(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(totalSteps) { index ->
                Text(
                    text = steps[index],
                    color = if (index <= currentStep) AccentGreen else NeutralGray500,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        //taking the center of the text

                        val parent = coordinates.parentLayoutCoordinates ?: return@onGloballyPositioned
                        val parentWidth = parent.size.width.toFloat()
                        val centerX = (coordinates.positionInParent().x + coordinates.size.width / 2) / parentWidth

                        // store normalized center (0f..1f)
                        if (center.size < steps.size) {
                            center.add(centerX)
                        } else {
                            center[index] = centerX
                        }

                    }/*.horizontalScroll(
                        remember { rememberScrollState() }
                    )*/
                )
            }
        }

        if (center.size == steps.size) {
            // Clamp currentStep in range
            val safeStep = currentStep.coerceIn(0, steps.lastIndex)
            val targetProgress = when{
                safeStep == 0 -> 0.05f
                safeStep >0 && safeStep<steps.lastIndex -> center[safeStep]
                else -> 1f
            }

            val animateProgress by animateFloatAsState(
                targetValue = targetProgress,
                animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing),
                label = "progress"
            )

            LinearProgressIndicator(
                progress = { animateProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = progressColor,
                trackColor = trackColor,
                strokeCap = StrokeCap.Round,
                gapSize = 0.dp
            )
        }
    }
}