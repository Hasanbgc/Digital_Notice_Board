package home.component

import KottieAnimation
import PrimaryBlue
import ViolateGradiant
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import home.getAnimation
import utils.KottieConstants

@Composable
fun FloatingAddButton(modifier: Modifier = Modifier, icon: Int = 0, onClick: () -> Unit) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val anim = getAnimation("files/breathing.json", KottieConstants.IterateForever, true)
        KottieAnimation(
            composition = anim.first,
            modifier = Modifier.fillMaxSize(),
            progress = { anim.second.progress }
        )
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier.padding(10.dp),
            shape = RoundedCornerShape(50.dp),
            containerColor = Color.Transparent, // IMPORTANT
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = ViolateGradiant,
                        shape = RoundedCornerShape(30.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "fab",
                    tint = Color.White
                )
            }
        }
    }

}