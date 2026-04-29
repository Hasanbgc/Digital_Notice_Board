package com.hasan.dnb.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import home.component.FloatingAddButton
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.BottleGreen
import presentation.MainDestination
import presentation.SecondaryTextAlt

data class BottomNavItem(
    val destination: MainDestination,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon,
    val label: String
) : NavKey

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
    onItemClick: (MainDestination) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 2.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(64.dp))
        )
        {
            // 🔹 Frosted glass background
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.8f),
                                Color.White.copy(alpha = 1f)
                            )
                        )
                    )
                    .blur(14.dp)
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
                        onClick = { onItemClick(item.destination) },
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

        FloatingAddButton(
            modifier = Modifier.size(70.dp).align(Alignment.CenterVertically),
        ) { onItemClick(MainDestination.CreateNotice) }
    }

}

@Preview
@Composable
fun SwipeableBottomNavigationBarPreview() {
    SwipeableBottomNavigationBar(
        selectedIndex = bottomNavItems[0],
        onItemClick = {},
        modifier = Modifier
    )
}