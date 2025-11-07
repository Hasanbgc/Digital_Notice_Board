import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/*
@Composable
fun String.asSvgImage(modifier: Modifier = Modifier) {
    val svgData = useResource("$this.svg") { it.readBytes() }
    val svg = SVGDOM(Data.makeFromBytes(svgData))
    val painter = rememberSvgPainter(svg)

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
    )
}*/
fun Modifier.cornerStretchAnimation(): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "cornerStretch")

    // Scale animation (grow and shrink)
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Slight rotation for diagonal “stretch” effect
    // Rotation along X-axis (top/bottom tilt)
    val rotationX by infiniteTransition.animateFloat(
        initialValue = 4f, // top up, bottom down
        targetValue = -4f,   // top down, bottom up
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotationX"
    )

    // Rotation along Y-axis (left/right tilt)
    val rotationY by infiniteTransition.animateFloat(
        initialValue = -6f,  // left closer, right farther
        targetValue = 6f,  // left farther, right closer
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotationY"
    )

    // Apply transformations
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
        this.rotationX = rotationX
        this.rotationY = rotationY
        transformOrigin = TransformOrigin(0.1f, 0.8f) // center pivot
    }
}

@Composable
fun animateToFocusView(): Color{
    val infiniteTransition = rememberInfiniteTransition("border color animation")

    val borderColor by infiniteTransition.animateColor(
        initialValue = EmergenceyAlertRedBG,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        )
    )
    return borderColor
}


fun Modifier.NeonEffect(
    colors: List<Color> = listOf(EmergencyIconBG,EmergenceyAlertRedBG, EmergencyIconBG),
    cornerRadius: Dp = 8.dp,
    borderWidth: Dp = 2.dp
) = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "neon_border_anim")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset(0f, 0f),
        end = Offset(offset, offset)
    )

    this
        .border(
            width = borderWidth,
            brush = brush,
            shape = RoundedCornerShape(cornerRadius)
        )
        .shadow(
            elevation = 20.dp,
            shape = RoundedCornerShape(cornerRadius),
            ambientColor = colors.first().copy(alpha = 0.5f),
            spotColor = colors.last().copy(alpha = 0.5f)
        )
}






