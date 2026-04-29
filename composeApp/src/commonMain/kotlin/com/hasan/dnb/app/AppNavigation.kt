package com.hasan.dnb.app

import authScreen.AuthScreenRoot
import authScreen.AuthViewModel
import GoogleAuthProvider
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import presentation.AppDestination

val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(AppDestination.Auth::class)
            subclass(AppDestination.Main::class)

            //subclass(AppDestination.Registration::class)
        }
    }
}

@Composable
fun AppNavigation(dest: AppDestination = AppDestination.Auth) {

    val backStack = rememberNavBackStack(config, dest)

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
                val viewModel: AuthViewModel = koinViewModel()
                val googleAuthProvider: GoogleAuthProvider = koinInject()
                AuthScreenRoot(
                    authViewModel = viewModel,
                    googleAuthProvider = googleAuthProvider,
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onLoginSuccess = {
                        backStack.clear()
                        backStack.add(AppDestination.Main(viewModel.userSession))
                    }
                )
            }
            entry<AppDestination.Main> { args ->
                MainNestedNavigation(args.userSession, onNavigate = {})
            }

            /* entry<AppDestination.Registration> {
                 val viewModel: RegistrationViewModel = koinViewModel()
                 RegistrationScreenRoot(viewModel, onBack = {
                     backStack.removeLastOrNull()
                 }, onRegistrationSuccess = {
                     backStack.clear()
                     backStack.add(AppDestination.Main)
                 })
             }*/

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