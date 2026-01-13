package com.hasan.dnb.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import presentation.BottleGreen
import presentation.DeepGreen
import presentation.GradientGreen
import presentation.GradientIndicatorBg
import presentation.PrimaryBlue
import presentation.PrimaryTextAlt2
import presentation.SecondaryBlue
import presentation.SecondaryGreen
import presentation.SecondaryTextAlt
import presentation.Violate

data class BottomNavItem(
    val destination: MainDestination,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val label: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        destination = MainDestination.Home,
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home,
        label = "Home"
    ),
    BottomNavItem(
        destination = MainDestination.Profile,
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person,
        label = "Profile"
    ),
    BottomNavItem(
        destination = MainDestination.Settings,
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        label = "Settings"
    )
)

@Composable
fun SwipeableBottomNavigationBar(
    selectedIndex: BottomNavItem,
    onItemClick: (Int) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(64.dp))
    ) {
        // 🔹 Frosted glass background
        Box(
            modifier = Modifier
                .matchParentSize()
                .blur(14.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.8f),
                            Color.White.copy(alpha = 1f)
                        )
                    )
                )
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    RoundedCornerShape(16.dp)
                )
        )

        // 🔹 Navigation bar
        NavigationBar(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent),
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0)
        ) {
            bottomNavItems.forEachIndexed { index, item ->
                val isSelected = selectedIndex == item

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.icon,
                            contentDescription = item.label
                        )
                    },
                    label = { Text(item.label) },
                    selected = isSelected,
                    onClick = { onItemClick(index) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        indicatorColor = BottleGreen,
                        selectedTextColor = BottleGreen,
                        unselectedTextColor = SecondaryTextAlt
                    )
                )
            }
        }
    }

}
