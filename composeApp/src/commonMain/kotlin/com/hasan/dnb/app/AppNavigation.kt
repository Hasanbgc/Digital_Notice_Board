package com.hasan.dnb.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import login.LoginScreenRoot
import login.LoginViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import registration.RegistrationScreenRoot
import registration.RegistrationViewModel

val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class){
            subclass(AppDestination.Auth::class)
            subclass(AppDestination.Registration::class)
            subclass(AppDestination.Main::class)
        }
    }
}

@Composable
@Preview
fun AppNavigation() {

    val backStack = rememberNavBackStack(config, AppDestination.Main)

    NavDisplay(
        backStack = backStack,
        modifier = Modifier.fillMaxSize(),
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<AppDestination.Auth> {
                val viewModel: LoginViewModel = viewModel()
                LoginScreenRoot(
                    viewModel,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(AppDestination.Main)
                    }
                )
            }
            entry<AppDestination.Registration> {
                val viewModel: RegistrationViewModel = viewModel()
                RegistrationScreenRoot(viewModel, onBack = {
                    backStack.removeLastOrNull()
                }, onRegistrationSuccess = {
                    backStack.clear()
                    backStack.add(AppDestination.Main)
                })
            }
            entry<AppDestination.Main> {
                HomeScreen()
            }
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInHorizontally(initialOffsetX = { it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { -it })
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            slideInHorizontally(initialOffsetX = { -it }) togetherWith
                    slideOutHorizontally(targetOffsetX = { it })
        }
    )
}