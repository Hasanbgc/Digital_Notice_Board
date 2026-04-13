package com.hasan.dnb.app

import Profile.ProfileScreenRoot
import Settings.SettingsScreenRoot
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import home.HomeScreenRoot
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreenHost(
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { HomeTab.entries.size })

    val currentScreen by remember {
        derivedStateOf { HomeTab.entries[pagerState.currentPage] }
    }
    // Get the coroutine scope from Compose
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        //haptic feedback
    }


    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ){
        HomeTab.entries.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = Color.Black
                        )
                    )
                }
            )
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            )
            { page ->
                val destination = HomeTab.entries[page]
                when (destination) {
                    HomeTab.FOR_YOU -> {
                        /*HomeScreen(
                            onNavigateToDetail = {}
                        )*/
                    }

                    HomeTab.NEARBY -> {
                        ProfileScreenRoot() {}
                    }

                    HomeTab.SAVED -> {
                        SettingsScreenRoot() {}
                    }
                }

            }

          /*  SwipeableBottomNavigationBar(
                selectedIndex = currentScreen,
                onItemClick = { index ->
                },
                modifier = modifier.align(Alignment.BottomCenter)
            )*/
        }
    }

}

enum class HomeTab(val title:String){
    FOR_YOU("For You"),
    NEARBY("Nearby"),
    SAVED("saved")

}

@Composable
@Preview()
fun HomeScreenPreview() {
    HomeScreenHost(
        modifier = Modifier,
        onNavigate = {}
    )
}
