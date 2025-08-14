package login

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import gray_light
import greenTextColor
import login.component.PhoneNumberTextBoxWithCountryCode
import loginBackground
import md_theme_light_background
import org.jetbrains.compose.ui.tooling.preview.Preview
import redTextColor
import signInTextColor
import tabBackground
import welcomeTextColor

@Composable
fun LoginScreenRoot(
    paddingValues: PaddingValues,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val tabState by viewModel.tabSelected.collectAsStateWithLifecycle()
    LoginScreen(
        paddingValues = paddingValues,
        tabState = tabState,
        onLoginSuccess = { onLoginSuccess.invoke() },
        onTabSelected = { viewModel.setTab(it) }
    )

}

@Preview()
@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    tabState: AuthTab,
    onLoginSuccess: () -> Unit,
    onTabSelected: (AuthTab) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().background(loginBackground).padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "Welcome Back",
            textAlign = TextAlign.Center,
            color = welcomeTextColor,
            style = MaterialTheme.typography.displaySmall,

            )
        Text(
            text = "Sign in to your account",
            textAlign = TextAlign.Center,
            color = signInTextColor,
            style = MaterialTheme.typography.titleSmall,

            )
        Spacer(modifier = Modifier.height(8.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxHeight(0.7f).fillMaxWidth(0.9f),
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.elevatedCardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(16.dp)
        ) {
            //Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = tabState.ordinal,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = tabBackground, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                containerColor = tabBackground,
                divider = {},
                indicator = { tabPositions ->
                    val tab = tabPositions[tabState.ordinal]

                    val offsetX by animateDpAsState(
                        targetValue = tab.left,
                        animationSpec = tween(durationMillis = 250),
                        label = "indicatorX"
                    )

                    val width by animateDpAsState(
                        targetValue = tab.width,
                        animationSpec = tween(durationMillis = 250),
                        label = "indicatorWidth"
                    )

                    Box(
                        Modifier
                            .offset(x = offsetX)
                            .width(tab.width)
                            .fillMaxHeight()
                            .padding(2.dp) // Optional padding inside
                            .background(Color.White, RoundedCornerShape(8.dp))
                    )
                }
            ) {
                Tab(
                    selected = tabState == AuthTab.NORMAL_USER,
                    onClick = { onTabSelected(AuthTab.NORMAL_USER) },
                    modifier = Modifier.padding(4.dp),
                    text = { Text(text = "Normal User") },
                    selectedContentColor = greenTextColor,
                    unselectedContentColor = Color.Black
                )
                Tab(
                    selected = tabState == AuthTab.NOTICE_POSTER,
                    onClick = { onTabSelected(AuthTab.NOTICE_POSTER) },
                    modifier = Modifier.padding(4.dp),
                    text = { Text(text = "Notice Poster") },
                    selectedContentColor = redTextColor,
                    unselectedContentColor = Color.Black
                )
            }
            when (tabState) {
                AuthTab.NORMAL_USER -> NormalUserLogin(/*onLoginSuccess = onLoginSuccess*/)
                AuthTab.NOTICE_POSTER -> NoticePosterLogin(onLoginSuccess = onLoginSuccess)
            }

        }


    }


}

@Preview
@Composable
fun NormalUserLogin(/*onLoginSuccess: () -> Unit*/) {
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var isError by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White).padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Phone Number",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        PhoneNumberTextBoxWithCountryCode(
            phoneNumber = phoneNumber,
            onPhoneNumberChange = {
                phoneNumber = it
                isError = validatePhoneNumber(it) { msg ->
                    errorMessage = msg
                }
            },
            countryCode = "+880",
            onCountryCodeChange = {},
            isError = isError,
            errorMessage = errorMessage,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun NoticePosterLogin(onLoginSuccess: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Notice Poster Login")
    }
}

fun validatePhoneNumber(phone: String, onInvalid: (String) -> Unit): Boolean {
    return when {
        phone.isBlank() -> {
            onInvalid("Phone number cannot be blank")
            false
        }

        phone.length < 10 -> {
            onInvalid("Phone number must be at least 10 digits")
            false
        }

        !phone.replace(Regex("[^\\d]"), "").matches(Regex("\\d{10,15}")) -> {
            onInvalid("Please enter a valid phone number")
            false
        }

        else -> {
            onInvalid("")
            true
        }
    }
}