package registration

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import common.AuthTab
import digita_notice_board.feature.auth.generated.resources.Res
import digita_notice_board.feature.auth.generated.resources.iphone_24
import digita_notice_board.feature.auth.generated.resources.shield
import gray_light
import greenTextColor
import login.LoginScreenState
import login.NoRippleInteractionSource
import login.NormalUserLogin
import login.NoticePosterLogin
import login.component.TabWithHorizontalIcon
import loginBackground
import org.jetbrains.compose.resources.painterResource
import redTextColor
import signInTextColor
import tabBackground
import textGray500
import welcomeTextColor
import kotlin.math.log

@Composable
fun RegistrationScreenRoot(
    paddingValues: PaddingValues,
    viewModel: RegistrationViewModel,
    onRegistrationSuccess: () -> Unit
) {
    val state by viewModel.registrationScreenState.collectAsStateWithLifecycle()
    RegistrationScreen(
        safePadding = paddingValues,
        state = state,
        viewModel = viewModel,
        onAction = viewModel::onAction,
        onRegistrationSuccess = {
            onRegistrationSuccess()
        }
    )

}

@Composable
fun RegistrationScreen(
    safePadding: PaddingValues,
    state: RegistrationScreenState,
    viewModel: RegistrationViewModel,
    onAction: (RegistrationScreenAction) -> Unit,
    onRegistrationSuccess: () -> Unit
) {

    onAction(
        RegistrationScreenAction.NormalUser.onSendCodeClick
    )
    viewModel.updateRegistrationState {
        copy(
            isLoading = false
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(loginBackground),
        color = Color.Transparent,
        tonalElevation = 8.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(safePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(60.dp))
            Column(
                modifier = Modifier.padding(horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = MaterialTheme.typography.displaySmall,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Join your local community and stay updated with important notices",
                    textAlign = TextAlign.Center,
                    color = textGray500,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            ElevatedCard(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.9f),
                shape = CardDefaults.elevatedShape,
                colors = CardDefaults.elevatedCardColors(Color.White),
                elevation = CardDefaults.elevatedCardElevation(16.dp)
            )
            {
                var tabPosition by remember { mutableStateOf<List<TabPosition>>(emptyList()) }
                val currentTab = state.selectedTab
                // Wrap TabRow in a Box to control layering
                Spacer(modifier = Modifier.height(16.dp))
                Box {
                    // Background indicator layer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Match TabRow height
                            .padding(6.dp)
                            .background(color = tabBackground, shape = RoundedCornerShape(32.dp))
                    )
                    {
                        if (tabPosition.isNotEmpty()) {
                            val selectedTab = tabPosition[currentTab.ordinal]

                            val animatedOffset by animateDpAsState(
                                targetValue = if (currentTab.ordinal == 0) selectedTab.left else selectedTab.left - 12.dp,
                                animationSpec = tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                ),
                                label = "indicatorOffset"
                            )

                            // Background indicator
                            Box(
                                modifier = Modifier
                                    .offset(x = animatedOffset)
                                    .width(selectedTab.width) // Adjust to match your tab width
                                    .fillMaxHeight()
                                    .padding(2.dp)
                                    .background(
                                        Color.White,
                                        RoundedCornerShape(32.dp)
                                    )
                            )
                        }
                    }

                    // TabRow on top
                    TabRow(
                        selectedTabIndex = currentTab.ordinal,
                        modifier = Modifier
                            .fillMaxWidth(),
                        containerColor = Color.Transparent, // Make transparent to show background
                        divider = {},
                        indicator = { indicator ->
                            tabPosition = indicator
                        } // Empty indicator
                    ) {
                        Tab(
                            selected = currentTab == AuthTab.NORMAL_USER,
                            onClick = {
                                viewModel.updateRegistrationState {
                                    copy(selectedTab = AuthTab.NORMAL_USER)
                                }
                            },
                            modifier = Modifier.padding(4.dp),
                            text = {
                                TabWithHorizontalIcon(
                                    text = "Normal user",
                                    iconRes = Res.drawable.iphone_24,
                                    contentDescription = "normal user icon",
                                    iconSize = 14.dp,
                                    spacing = 4.dp
                                )
                            },
                            selectedContentColor = greenTextColor,
                            unselectedContentColor = Color.Black,
                            interactionSource = remember { NoRippleInteractionSource() }
                        )
                        Tab(
                            selected = currentTab == AuthTab.NOTICE_POSTER,
                            onClick = {
                                viewModel.updateRegistrationState {
                                    copy(selectedTab = AuthTab.NOTICE_POSTER)
                                }
                            },
                            modifier = Modifier.padding(4.dp),
                            text = {
                                TabWithHorizontalIcon(
                                    text = "Notice Poster",
                                    iconRes = Res.drawable.shield,
                                    contentDescription = "notice poster icon",
                                    iconSize = 14.dp,
                                    spacing = 4.dp
                                )
                            },
                            selectedContentColor = redTextColor,
                            unselectedContentColor = Color.Black,
                            interactionSource = remember { NoRippleInteractionSource() }
                        )
                    }
                }
                /*when (currentTab) {
                    AuthTab.NORMAL_USER -> NormalUserLogin(*//*onLoginSuccess = onLoginSuccess*//*
                        state = loginScreenState,
                        onStateChange = onStateChange,
                        onAction =onAction
                    )
                    AuthTab.NOTICE_POSTER -> NoticePosterLogin(
                        state = loginScreenState,
                        onStateChange = onStateChange,
                        onAction = onAction
                    )
                }*/
                Spacer(modifier = Modifier.height(16.dp))

            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "By registering you agree to our Terms of Service and Privacy Policy",
                style = MaterialTheme.typography.bodyMedium,
                color = textGray500,
            )

        }

    }


}