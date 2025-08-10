package com.hasan.dnb.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

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
    selectedIndex:Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier
) {
    /*val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination*/

    NavigationBar(
        modifier = modifier
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index

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
                    /*navController.navigate(item.destination) {
                        // Pop up to the start destination and save state
                        popUpTo(MainDestination.Home) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting
                        restoreState = true
                    }*/
                }
            )
        }
    }
}
