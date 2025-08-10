package home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel,
    onNavigateToDetail: (String) -> Unit
){
    //val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        //state = state,
        onNavigateToDetail = onNavigateToDetail
    )

}

@Composable
fun HomeScreen(
    //state: state,
    onNavigateToDetail: (String) -> Unit
){
    // UI code
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Home Screen")

    }

}