package home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoundGradientButton(
    modifier : Modifier,
    text : String = "Button",
    icon: Any,
    iconTint : Color = Color.White,
    gradientColors: Brush,
    shape: Shape = RoundedCornerShape(30.dp),
    onClick: () -> Unit = {},
){
    Card(modifier = modifier
        .wrapContentSize()
        .clickable{
           onClick()
        },
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Box(
            modifier = Modifier
                .background(
                    brush = gradientColors,
                    shape = shape
                )
                .padding(4.dp)
        ) {
            when(icon){
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = text,
                    tint = iconTint,
                    modifier = Modifier
                )
                is DrawableResource -> Icon(
                    painter = painterResource(icon),
                    contentDescription = text,
                    tint = iconTint,
                    modifier = Modifier
                )
            }
        }
    }

}