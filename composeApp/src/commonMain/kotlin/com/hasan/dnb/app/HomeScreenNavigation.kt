package com.hasan.dnb.app

import Profile.ProfileScreenRoot
import Settings.SettingsScreenRoot
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import home.HomeScreenRoot
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { bottomNavItems.size })

    val currentScreen by remember {
        derivedStateOf { bottomNavItems[pagerState.currentPage] }
    }
    // Get the coroutine scope from Compose
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        //haptic feedback
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            )
            { page ->
                val destination = bottomNavItems[page].destination
                when (destination) {
                    MainDestination.Home -> {
                        HomeScreenRoot(
                            onNavigateToDetail = {}
                        )
                    }

                    MainDestination.Profile -> {
                        ProfileScreenRoot() {}
                    }

                    MainDestination.Settings -> {
                        SettingsScreenRoot() {}
                    }
                    MainDestination.CreateNotice -> {

                    }
                }

            }

            SwipeableBottomNavigationBar(
                selectedIndex = currentScreen,
                onItemClick = { index ->
                },
                modifier = modifier.align(Alignment.BottomCenter)
            )
        }
    }

}