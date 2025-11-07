package Settings

import Settings.SettingsViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel,
    onBackPressed: () -> Unit
){
    //val state by viewModel.state.collectAsStateWithLifecycle()

        SettingsScreen(
            // state = state,
            onBackPressed = onBackPressed,
            viewModel
        )
    CoroutineScope(Dispatchers.Main).launch {
        runBasicFlow(viewModel)
    }
}
@Composable
fun SettingsScreen(
    //state:SettingsState,
    onBackPressed: () -> Unit,
    vm: SettingsViewModel
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Settings Screen")

    }
}
suspend fun runBasicFlow(viewModel: SettingsViewModel){
   /* println("flow_before collect")
    viewModel.basicFlowExample().collect { value ->
        println("flow_Received value: $value")
    }
    println("flow_after collect")*/


    //viewModel.coldFlowDemo()

    /*viewModel.flowBuilder().collect {
        println("flow_Received value: $it")
    }

    viewModel.flowOfExample().collect {
        println("flow_Received value: $it")
    }

    viewModel.asFlowExample().collect {
        println("flow_Received value: $it")
    }*/

   /* viewModel.mapExample().collect {
        println("flow_Received value: $it")
    }

    viewModel.filterExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.transformExample().collect {
        println("flow_Received value: $it")
    }

    viewModel.takeExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.distinctUntilChangedExample().collect {
        println("flow_Received value: $it")
    }

    viewModel.mergeExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.combineExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.flatMapConcatExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.onStartExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.onCompleteExample().collect {
        println("flow_Received value: $it")
    }
    viewModel.catchExceptionExample().collect {
        println("flow_Received value: $it")
    }*/
    viewModel.shareInExample(CoroutineScope(Dispatchers.Main)).collect {
        println("flow_Received value: $it")
    }







}