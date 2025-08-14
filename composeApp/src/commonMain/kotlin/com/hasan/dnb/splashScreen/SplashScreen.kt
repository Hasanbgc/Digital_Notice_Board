package com.hasan.dnb.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import md_theme_light_onPrimary
import splashScreenBackground
import digita_notice_board.composeapp.generated.resources.Res
import digita_notice_board.composeapp.generated.resources.app_name
import digita_notice_board.composeapp.generated.resources.dnb_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SplashScreenRoot(
    viewModel: SplashViewModel,
    navigateTo: (String) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    SplashScreen(
        state = state,
        onComplete ={
            when(state){
                is SplashScreenAction.Complete -> navigateTo(Constant.AUTH)
                else -> Unit
            }
        }
    )

}

@Preview()
@Composable
fun SplashScreen(
    state: SplashScreenAction,
    onComplete: () -> Unit
) {
    LaunchedEffect(state){
        if (state is SplashScreenAction.Complete) {
            onComplete()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().background(splashScreenBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.45f))
        Image(
            painter = painterResource(Res.drawable.dnb_logo),
            contentDescription = "Logo",
            modifier = Modifier.width(120.dp).height(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "ডিজিটাল নোটিশ বোর্ড",
            color = md_theme_light_onPrimary,
            style = MaterialTheme.typography.headlineLarge,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.app_name),
            color = md_theme_light_onPrimary,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(160.dp))
        if(state is SplashScreenAction.Loading){
            CircularProgressIndicator(
                color = md_theme_light_onPrimary
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

