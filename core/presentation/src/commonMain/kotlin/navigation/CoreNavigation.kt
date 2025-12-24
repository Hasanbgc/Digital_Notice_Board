package navigation

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
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val config = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class){
            subclass(AppDestination.Splash::class)
            subclass(AppDestination.Auth::class)
            subclass(AppDestination.Registration::class)
            subclass(AppDestination.Main::class)
        }

    }
}

@Composable
fun CoreNavigation(){
    val backStack = rememberNavBackStack(config, AppDestination.Splash)

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
            entry<AppDestination.Splash> {
               /* val viewModel: SplashViewModel = viewModel()
                SplashScreenRoot(viewModel) { destination ->
                    when (destination) {
                        Constant.MAIN -> navController.navigate(AppDestination.Main) {
                            popUpTo(AppDestination.Splash) { inclusive = true }
                        }

                        Constant.AUTH -> navController.navigate(AppDestination.Auth) {
                            popUpTo(AppDestination.Splash) { inclusive = true }
                        }

                        else -> {}
                    }
                }*/
            }
        }
    )

}