package com.hasan.dnb.app

import Profile.ProfileScreenRoot
import Profile.ProfileViewModel
import Settings.SettingsScreenRoot
import Settings.SettingsViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import home.HomeScreenRoot
import home.HomeViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    //val navController = rememberNavController()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { bottomNavItems.size }
    )
    // Get the coroutine scope from Compose
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage){
        //haptic feedback
    }

    Scaffold(
        bottomBar = {
            SwipeableBottomNavigationBar(
                selectedIndex = pagerState.currentPage,
                onItemClick = { index ->
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = modifier
            )
        },
        modifier = modifier
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){page ->
            when(page){
                0 -> {
                    val viewModel: HomeViewModel = viewModel()
                    HomeScreenRoot(viewModel){

                    }
                }
                1 -> {
                    val viewModel: ProfileViewModel = viewModel()
                    ProfileScreenRoot(viewModel){

                    }
                }
                2 -> {
                    val viewModel: SettingsViewModel = viewModel()
                    SettingsScreenRoot(viewModel){

                    }
                }
            }

        /*NavHost(
            navController = navController,
            startDestination = MainDestination.Home,
            modifier = modifier.fillMaxSize().padding(paddingValues)
        ) {
           composable<MainDestination.Home>{
               val viewModel: HomeViewModel = viewModel()
               HomeScreenRoot(viewModel){

               }
           }
            composable<MainDestination.Profile> {
                val viewModel: ProfileViewModel = viewModel()
                ProfileScreenRoot(viewModel){

                }
            }
            composable<MainDestination.Settings> {
                val viewModel: SettingsViewModel = viewModel()
                SettingsScreenRoot(viewModel){

                }
            }*/
        }
    }
}