package com.hasan.dnb.app

import Profile.ProfileScreenRoot
import Settings.SettingsScreenRoot
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import createNotice.CreateNoticeRoot
import home.HomeScreenRoot
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass


val saveStateConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(MainDestination.Home::class)
            subclass(MainDestination.Profile::class)
            subclass(MainDestination.Settings::class)
            subclass(MainDestination.CreateNotice::class)
        }
    }
}

@Composable
fun MainNestedNavigation(onNavigate: () -> Unit) {
    val backStack = rememberNavBackStack(saveStateConfig, MainDestination.Home)

    val currentDestination by remember{
        derivedStateOf {
            bottomNavItems.find { it.destination == backStack.lastOrNull() }
                ?: bottomNavItems[0]
        }
    }

    val bottomNavVisible by remember{
        derivedStateOf {
            backStack.lastOrNull() !is MainDestination.CreateNotice
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            NavDisplay(
                backStack = backStack,
                modifier = Modifier
                    .fillMaxSize(),
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    //  rememberViewModelStoreNavEntryDecorator(),
                ),
                onBack = {
                    backStack.removeLastOrNull()
                },
                entryProvider = entryProvider {
                    entry<MainDestination.Home> {
                        HomeScreenRoot(
                            onNavigateToDetail = {},
                        )
                    }
                    entry<MainDestination.Profile> {
                        ProfileScreenRoot() {}
                    }

                    entry<MainDestination.Settings> {
                        SettingsScreenRoot() {}
                    }
                    entry<MainDestination.CreateNotice> {
                         CreateNoticeRoot(){
                             backStack.removeLastOrNull()
                         }
                    }
                }
            )

            AnimatedVisibility(
                visible = bottomNavVisible,
                enter = slideInHorizontally (initialOffsetX = { it }) + fadeIn(),
                exit = slideOutHorizontally (targetOffsetX = { it }) + fadeOut()
            ) {
                SwipeableBottomNavigationBar(
                    selectedIndex = currentDestination,
                    onItemClick = { destination ->
                        // Don't re-add if already on that tab
                        if (backStack.lastOrNull() == destination) return@SwipeableBottomNavigationBar

                        // Pop back to home if navigating between tabs (standard M3 behavior)
                        while (backStack.size > 1) backStack.removeLastOrNull()

                        backStack.add(destination)
                        //currentDestination = bottomNavItems[index]
                    },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }

    }
}