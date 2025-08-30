package login

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontStyle
/*import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info*/
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import boarder
import continueButtonBackground
import deep_green
import digita_notice_board.feature.auth.generated.resources.Res
import digita_notice_board.feature.auth.generated.resources.facebook
import digita_notice_board.feature.auth.generated.resources.flag_bd
import digita_notice_board.feature.auth.generated.resources.google
import digita_notice_board.feature.auth.generated.resources.iphone_24
import digita_notice_board.feature.auth.generated.resources.right_arrow
import digita_notice_board.feature.auth.generated.resources.shield
import gray_light
import green400
import greenTextColor
import green_light_boarder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import light_green
import login.component.PhoneNumberTextBoxWithCountryCode
import login.component.RoundCornerIconButton
import login.component.TabWithHorizontalIcon
import loginBackground
import md_theme_light_background
import mid_green
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import redTextColor
import signInTextColor
import tabBackground
import textGray500
import welcomeTextColor

@Composable
fun LoginScreenRoot(
    paddingValues: PaddingValues,
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val loginScreenState by viewModel.loginScreenState.collectAsStateWithLifecycle()
    LoginScreen(
        paddingValues = paddingValues, //paddingValues,
        loginScreenState = loginScreenState,
        onStateChange = { viewModel.updateLoginState(it) },
    )

}

class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction): Boolean = true
}

@Preview()
@Composable
fun LoginScreen(
    paddingValues: PaddingValues,
    loginScreenState: LoginScreenState,
    onStateChange: (LoginScreenState) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(loginBackground)
            .padding(paddingValues),
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
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth(0.9f),
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.elevatedCardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(16.dp)
        )
        {
            var tabPosition by remember { mutableStateOf<List<TabPosition>>(emptyList()) }
            val currentTab = loginScreenState.selectedTab
            // Wrap TabRow in a Box to control layering
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
                        onClick = { onStateChange(loginScreenState.copy(selectedTab = AuthTab.NORMAL_USER)) },
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
                        onClick = {onStateChange(LoginScreenState(selectedTab = AuthTab.NOTICE_POSTER)) },
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
            when (currentTab) {
                AuthTab.NORMAL_USER -> NormalUserLogin(/*onLoginSuccess = onLoginSuccess*/
                    state = loginScreenState,
                    onStateChange = onStateChange,
                    )
                AuthTab.NOTICE_POSTER -> NoticePosterLogin(
                    state = loginScreenState,
                    onStateChange = onStateChange,
                )
            }
        }


    }
}

@Preview
@Composable
fun NormalUserLogin(
    state: LoginScreenState,
    onStateChange: (LoginScreenState) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var isError by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

    var isAutoRead by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // Clear focus on tap outside
                    focusManager.clearFocus()
                })
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    )
    {
        Text(
            text = "Phone Number",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.mobileNumber,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                if (filteredValue.length <= 12) {
                    onStateChange(state.copy(mobileNumber = filteredValue))
                }
            },
            placeholder = {
                Text(text = "18xxxxxxxxx", color = Color.Gray)
            },
            leadingIcon = {
                Row(
                    modifier = Modifier
                        .height(56.dp)
                        .background(color = gray_light,shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
                        .padding(start = 16.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Image(
                        painter = painterResource(Res.drawable.flag_bd),
                        contentDescription = "Country Flag",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(text = "+880")
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = boarder,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
            isError = isError,
            shape = RoundedCornerShape(16.dp)
        )
        Text(
            text = "We'll send you a verification code",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodySmall,
            color = textGray500
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(90.dp)
                .background(color = green400, RoundedCornerShape(16.dp))
                .border(1.dp, green_light_boarder, RoundedCornerShape(16.dp))
                .padding(start = 14.dp, top = 4.dp, bottom = 4.dp, end = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column{
                Text(
                    text = "Auto-read SMS",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    color = deep_green,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Automatically fill OTP from SMS",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 12.sp,
                    color = light_green
                )

            }

            Switch(
                checked = isAutoRead,
                onCheckedChange = { isChecked ->
                    isAutoRead = isChecked
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = mid_green,
                    checkedThumbColor = Color.White,
                    uncheckedTrackColor = Color.LightGray,
                    uncheckedThumbColor = Color.White,
                    uncheckedBorderColor = Color.White,
                    ),
                interactionSource = remember { NoRippleInteractionSource() }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
            contentAlignment = Alignment.Center
        ){
            Column(modifier = Modifier.height(1.dp).fillMaxWidth().background(Color.LightGray)) {  }
            Text(
                text = "or",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = textGray500,
                modifier = Modifier.background(Color.White).padding(start = 20.dp, end = 20.dp, bottom = 2.dp),

            )

        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            RoundCornerIconButton(
                text = "Google",
                iconRes = Res.drawable.google,
                contentDescription = "Google",
                backgroundColor = Color.White,
                paddingValues = PaddingValues(vertical = 10.dp, horizontal = 35.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            RoundCornerIconButton(
                text = "Facebook",
                iconRes = Res.drawable.facebook,
                contentDescription = "Apple",
                backgroundColor = Color.White,
                paddingValues = PaddingValues(vertical = 10.dp, horizontal = 28.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = continueButtonBackground,
                    shape = RoundedCornerShape(16.dp)
                ),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent // Make button background transparent
            ),
            contentPadding = PaddingValues(0.dp)

        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(Res.drawable.right_arrow),
                    contentDescription = "Continue arrow",
                    tint = Color.White
                )
            }

        }

    }
}

@Composable
fun NoticePosterLogin(
    state: LoginScreenState,
    onStateChange: (LoginScreenState) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isInputSecret by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    // Clear focus on tap outside
                    focusManager.clearFocus()
                })
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Registered Phone",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.regMobileNumber,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                if (filteredValue.length <= 12) {
                    onStateChange(state.copy(regMobileNumber = filteredValue))
                }
            },
            placeholder = {
                Text(text = "18xxxxxxxxx", color = Color.Gray)
            },
            leadingIcon = {
                Row(
                    modifier = Modifier
                        .height(56.dp)
                        .background(color = gray_light,shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
                        .padding(start = 16.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Image(
                        painter = painterResource(Res.drawable.flag_bd),
                        contentDescription = "Country Flag",
                        modifier = Modifier
                            .size(20.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(text = "+880")
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = boarder,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
            isError = isError,
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Security PIN",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                if(it.length<=4)
                onStateChange(state.copy(password = it))
            },
            placeholder = {
                Text(text = "Enter 4-digit PIN ", color = Color.Gray)
            },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = if(isInputSecret)Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Password visibility",
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        isInputSecret = !isInputSecret
                    }
                )
            },
            visualTransformation = if(isInputSecret){
                PasswordVisualTransformation(mask = '*')
            }else{
                VisualTransformation.None
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword, imeAction = ImeAction.Done),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = boarder,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )


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