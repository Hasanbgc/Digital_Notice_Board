package com.hasan.dnb.app

import Profile.ProfileScreenRoot
import Profile.ProfileViewModel
import Settings.SettingsScreenRoot
import Settings.SettingsViewModel
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
import androidx.lifecycle.viewmodel.compose.viewModel
import home.HomeScreenRoot
import home.HomeViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
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
                        val viewModel: HomeViewModel = koinViewModel()
                        HomeScreenRoot(viewModel) {

                        }
                    }

                    MainDestination.Profile -> {
                        val viewModel: ProfileViewModel = koinViewModel()
                        ProfileScreenRoot(viewModel) {

                        }
                    }

                    MainDestination.Settings -> {
                        val viewModel: SettingsViewModel = koinViewModel()
                        SettingsScreenRoot(viewModel) {

                        }
                    }
                }

            }

            SwipeableBottomNavigationBar(
                selectedIndex = currentScreen,
                onItemClick = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = modifier.align(Alignment.BottomCenter)
            )
        }
    }

}