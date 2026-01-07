package com.hasan.dnb.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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
    /*val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination*/

    Box {
        // 🔹 Frosted glass background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .blur(16.dp)
                .background(brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.8f),
                        Color.White.copy(alpha = 1f)
                    )
                )).border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), )
        )
        NavigationBar(
            modifier = modifier.fillMaxWidth(),
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
                    label = {
                        Text(text = item.label)
                    },
                    selected = isSelected,
                    onClick = {
                        onItemClick(index)
                    }
                )
            }
        }
    }
}
