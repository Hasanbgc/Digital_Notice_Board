package home.component

import EmergencyIconBG
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin
import kotlin.math.PI

@Composable
fun CurvedCornerTriangle(
    color: Color = Color(0xFFB2FF59), // light green
    size: Dp = 40.dp,
    cornerRadius: Dp = 16.dp,
    waveAmplitude: Dp = 2.dp, // how far the wave goes horizontally
    waveFrequency: Int = 3,   // how many wave peaks along the edge
    modifier: Modifier = Modifier
)
{

    val radiusPx = with(LocalDensity.current) { cornerRadius.toPx() }
    val amplitudePx = with(LocalDensity.current) { waveAmplitude.toPx() }

    Canvas(modifier = modifier.size(size)) {
        val width = size.toPx()
        val height = size.toPx()

        val path = Path().apply {
            val r = radiusPx

            moveTo(0f, 0f)
            lineTo(width, 0f)
            //lineTo(0f, height)// top edge
            // Hypotenuse A -> C as sine wave
            val steps = 50
            for (i in 0..steps) {
                val t = i / steps.toFloat()
                val xBase = t * width
                val yBase = t * height
                // Sine offset perpendicular to diagonal
                val offset = amplitudePx * sin(2 * PI * waveFrequency * t).toFloat()
                val dx = width
                val dy = height
                val length = kotlin.math.hypot(dx, dy)
                val x = xBase - offset * (dy / length)
                val y = yBase + offset * (dx / length)
                lineTo(x, y)
            }

            lineTo(width, height) // Point C (bottom-right)
            close()
        }
        drawPath(path = path, color = color, style = Fill)
        //drawPath(path = path, color = Color.Black, style = Stroke(width = 2f))
    }
}



@Composable
fun WaveFilledShape(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(EmergencyIconBG)
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Points: A(0,0), B(width,0), C(width,height)
        val A = Offset(0f, 0f)
        val B = Offset(width, 0f)
        val C = Offset(width, height)

        val path = Path().apply {
            moveTo(A.x, A.y) // Start at A
            lineTo(B.x, B.y) // Line to B
            lineTo(C.x, C.y) // Line to C
            lineTo(A.x, A.y) // Line back to A
            close()
        }

        // Gradient fill
        val brush = if (colors.size == 1) {
            // Single color fill
            Brush.verticalGradient(
                colors = listOf(colors.first(), colors.first())
            )
        } else {
            // Multiple colors -> gradient
            Brush.linearGradient(
                colors = colors,
                start = Offset(0f, 0f),
                end = Offset(width, height)
            )
        }

        drawPath(
            path = path,
            brush = brush,
            style = Fill
        )
    }
}

// Preview usage:
// @Preview
// @Composable
// fun PreviewWave() {
//     Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1A1A2E))) {
//         WaveFilledShape()
//     }
// }
