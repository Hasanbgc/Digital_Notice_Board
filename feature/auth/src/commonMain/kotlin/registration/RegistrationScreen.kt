package registration

import AccentGreen
import AppMapView
import BorderBlue
import BorderGray
import DeepGreen
import ErrorRed
import LightGray
import NeutralGray500
import SecondaryGreen
import SurfaceBackground
import SurfaceBlue
import TabBackgroundGray
import TrackColor
import androidx.collection.SimpleArrayMap
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hasan.dnb.core.presentation.resources.flag_bd
import common.AuthTab
import continueButtonBackgroundActive
import continueButtonBackgroundInactive
import digita_notice_board.feature.auth.generated.resources.Res
import digita_notice_board.feature.auth.generated.resources.chat
import digita_notice_board.feature.auth.generated.resources.check_mark
import digita_notice_board.feature.auth.generated.resources.flag_bd
import digita_notice_board.feature.auth.generated.resources.iphone_24
import digita_notice_board.feature.auth.generated.resources.location
import digita_notice_board.feature.auth.generated.resources.next
import digita_notice_board.feature.auth.generated.resources.right_arrow
import digita_notice_board.feature.auth.generated.resources.send
import digita_notice_board.feature.auth.generated.resources.shield
import login.LoginScreenAction
import login.NoRippleInteractionSource
import login.component.TabWithHorizontalIcon
import loginBackground
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import registration.component.AnimatedProgressBar
import registration.component.OTPOutlinedTextBox
import kotlin.coroutines.Continuation

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
        onStateChange = { updatedState ->
            viewModel.updateRegistrationState(
                updateState = {
                    updatedState
                }
            )
        },
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
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit,
    onRegistrationSuccess: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(loginBackground),
        color = Color.Transparent,
        tonalElevation = 8.dp,
    ) {

        Box(modifier = Modifier.fillMaxSize().padding(safePadding)) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.94f)
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(40.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    /** welcome Text */
                    Text(
                        text = "Welcome",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        style = MaterialTheme.typography.displaySmall,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    /** subtitle */
                    Text(
                        text = "Join your local community and stay updated with important notices",
                        textAlign = TextAlign.Center,
                        color = NeutralGray500,
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
                    val scrollState = rememberScrollState()
                    // Wrap TabRow in a Box to control layering
                    Spacer(modifier = Modifier.height(6.dp))
                    Box {
                        // Background indicator layer
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp) // Match TabRow height
                                .padding(6.dp)
                                .background(
                                    color = TabBackgroundGray,
                                    shape = RoundedCornerShape(32.dp)
                                )
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
                                selectedContentColor = AccentGreen,
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
                                selectedContentColor = ErrorRed,
                                unselectedContentColor = Color.Black,
                                interactionSource = remember { NoRippleInteractionSource() }
                            )
                        }
                    }
                    /** content switch here */
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .verticalScroll(scrollState)
                            .wrapContentHeight(),
                    ) {
                        when (currentTab) {
                            AuthTab.NORMAL_USER -> RegisterNormalUser(
                                state = state,
                                viewModel = viewModel,
                                onStateChange = onStateChange,
                                onAction = onAction,
                                onRegistrationSuccess = onRegistrationSuccess
                            )

                            AuthTab.NOTICE_POSTER -> RegisterNoticePoster(
                                state = state,
                                viewModel = viewModel,
                                onStateChange = onStateChange,
                                onAction = onAction,
                                onRegistrationSuccess = onRegistrationSuccess
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }

                //Spacer(modifier = Modifier.weight(1f))


            }

            /** terms and condition text */
            Text(
                text = "By registering you agree to our Terms of Service and Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                color = NeutralGray500,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.BottomCenter).padding(horizontal = 16.dp)
            )
        }

    }


}


@Composable
fun RegisterNormalUser(
    state: RegistrationScreenState,
    viewModel: RegistrationViewModel,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    var currentStep by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.dp).background(SurfaceBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // region progress bar
        val listOfState = listOf("Phone", "Verify", "Location")
        AnimatedProgressBar(
            steps = listOfState,
            currentStep = state.progressState,
            trackColor = TrackColor,
            progressColor = AccentGreen
        )
        //endregion
        Spacer(modifier = Modifier.height(4.dp))

        when (state.progressState) {
            0 -> PhoneVerification(
                state = state,
                onStateChange = onStateChange,
                onAction = onAction
            )


            1 -> OTPCodeVerification(
                state = state,
                onStateChange = onStateChange,
                onAction = onAction
            )

            2 -> SetupLocation(
                state = state,
                onStateChange = onStateChange,
                onAction = onAction,
                onRegistrationSuccess = onRegistrationSuccess
            )
        }

    }

}

@Composable
fun PhoneVerification(
    state: RegistrationScreenState,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit
) {
    var isError by remember { mutableStateOf(false) }
    var sendCodeBtnEnable by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //region phone icon
        ElevatedCard(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(8.dp),
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {

            Image(
                painter = painterResource(Res.drawable.iphone_24),
                contentDescription = "phone icon",
                modifier = Modifier.fillMaxSize().padding(16.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(AccentGreen)
            )
        }
        //endregion
        Spacer(modifier = Modifier.height(8.dp))
        //region title and subtitle
        Text(
            text = "Verify Your Phone Number",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We'll send you a verification code via SMS",
            style = MaterialTheme.typography.bodySmall,
            color = NeutralGray500
        )
        //endregion
        Spacer(modifier = Modifier.height(12.dp))
        // region phone number text field
        Text(
            text = "Mobile Number",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.mobileNumber,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                if (filteredValue.length <= 11) {
                    onStateChange(state.copy(mobileNumber = filteredValue))
                }
                if (filteredValue.length >= 11) {
                    sendCodeBtnEnable = true
                } else {
                    sendCodeBtnEnable = false
                }
            },
            placeholder = {
                Text(text = "18xxxxxxxxx", color = Color.Gray)
            },
            leadingIcon = {
                Row(
                    modifier = Modifier
                        .height(56.dp)
                        .background(
                            color = LightGray,
                            shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                        )
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
                unfocusedIndicatorColor = BorderGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            isError = isError,
            shape = RoundedCornerShape(16.dp)
        )
        //endregion
        Spacer(modifier = Modifier.height(32.dp))
        //region send code button and info text
        Button(
            onClick = {
                onAction(RegistrationScreenAction.NormalUser.onSendCodeClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = sendCodeBtnEnable,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Make button background transparent
                disabledContainerColor = Color.Transparent, // Make button background transparent
            ),
            contentPadding = PaddingValues(0.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = if (sendCodeBtnEnable) continueButtonBackgroundActive else continueButtonBackgroundInactive,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.send),
                    contentDescription = "send icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Send Code",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.shield),
                contentDescription = "shield icon",
                tint = AccentGreen,
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Your information is secure",
                style = MaterialTheme.typography.bodySmall,
                color = NeutralGray500
            )
        }
        //endregion
    }
}

@Composable
fun OTPCodeVerification(
    state: RegistrationScreenState,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //region verification icon
        ElevatedCard(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(8.dp),
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {

            Image(
                painter = painterResource(Res.drawable.chat),
                contentDescription = "phone icon",
                modifier = Modifier.fillMaxSize().padding(16.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(AccentGreen)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        //endregion

        //region title
        Text(
            text = "Enter Verification Code",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        //endregion

        //region mobile number
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = SurfaceBlue, shape = RoundedCornerShape(16.dp))
                .border(width = 1.dp, color = BorderBlue, shape = RoundedCornerShape(16.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Code sent to",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "BD +880${state.mobileNumber}",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentGreen
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        //endregion

        //region otp text field
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.check_mark),
                contentDescription = "check icon",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "4-Digit Code",
                style = MaterialTheme.typography.bodyMedium,
                color = NeutralGray500,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OTPOutlinedTextBox(
            otpLength = 4,
            onOTPComplete = {
                onAction(RegistrationScreenAction.NormalUser.onOTPEntered(it))
            },
            isError = state.otpError,
        )
        Spacer(modifier = Modifier.height(6.dp))
        if (state.otpError) {
            Text(
                text = "OTP is not correct",
                style = MaterialTheme.typography.bodyMedium,
                color = ErrorRed
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        //endregion

        //region verify code button
        Button(
            onClick = {
                onAction(RegistrationScreenAction.NormalUser.onVerifyClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = state.verifyButtonEnabled,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Make button background transparent
                disabledContainerColor = Color.Transparent, // Make button background transparent
            ),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = if (state.verifyButtonEnabled) continueButtonBackgroundActive else continueButtonBackgroundInactive,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.check_mark),
                    contentDescription = "check icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Verify Code",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(12.dp))
        //endregion

        //region go back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "right arrow icon",
                    tint = NeutralGray500,
                    modifier = Modifier.size(16.dp).clickable {
                        onAction(RegistrationScreenAction.NormalUser.onGoBackClick)
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Go Back",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeutralGray500,
                    modifier = Modifier.clickable {
                        onAction(RegistrationScreenAction.NormalUser.onGoBackClick)
                    }
                )
            }

            Text(
                text = "Resend",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentGreen,
                modifier = Modifier.clickable {
                    onAction(RegistrationScreenAction.NormalUser.onResendClick)
                }
            )
        }
        //endregion

    }
}


@Composable
fun SetupLocation(
    state: RegistrationScreenState,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //region location icon
        ElevatedCard(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(8.dp),
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {

            Image(
                painter = painterResource(Res.drawable.location),
                contentDescription = "location icon",
                modifier = Modifier.fillMaxSize().padding(16.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(AccentGreen)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        //endregion

        //region title and subtitle
        Text(
            text = "Set Up Your Location",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Select your area to receive relevant local notices",
            style = MaterialTheme.typography.bodySmall,
            color = NeutralGray500
        )
        Spacer(modifier = Modifier.height(12.dp))
        //endregion

        // region Location callback function to update state with detected coordinates
        val onLocationDetected: (Double, Double) -> Unit = { lat, long ->
            onStateChange(
                state.copy(
                    latitude = lat,
                    longitude = long
                )
            )
            println("Current latitude: $lat, longitude: $long")
        }

        AppMapView(state.detectLocation, onLocationDetected)
        //endregion

        //region find btton
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onAction(RegistrationScreenAction.NormalUser.onFindLocationClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = true,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Make button background transparent
                disabledContainerColor = Color.Transparent, // Make button background transparent
            ),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = DeepGreen,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.send),
                    contentDescription = "send icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Find My Location",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

        }
        //endregion

        //region continue button
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onAction(RegistrationScreenAction.NormalUser.onCompleteClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = true,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Make button background transparent
                disabledContainerColor = Color.Transparent, // Make button background transparent
            ),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = continueButtonBackgroundActive,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.check_mark),
                    contentDescription = "send icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Complete Registration",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.right_arrow),
                        contentDescription = "go icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                if (state.navigateToLogin) {
                    onRegistrationSuccess.invoke()
                }
                if (state.navigateToMain) {
                    onRegistrationSuccess.invoke()
                }

            }
        }
        //endregion

        //region skip button
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                onRegistrationSuccess.invoke()
                //onAction(RegistrationScreenAction.NormalUser.onSkipClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            enabled = true,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent, // Make button background transparent
                disabledContainerColor = Color.Transparent, // Make button background transparent
            ),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.next),
                    contentDescription = "next icon",
                    tint = NeutralGray500,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Skip for now",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeutralGray500
                )


            }

        }
        Spacer(modifier = Modifier.height(12.dp))
        //endregion
    }
}

@Composable
fun RegisterNoticePoster(
    state: RegistrationScreenState,
    viewModel: RegistrationViewModel,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit,
    onRegistrationSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //region notice poster icon
        ElevatedCard(
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(8.dp),
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(Color.White),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {

            Image(
                painter = painterResource(Res.drawable.iphone_24),
                contentDescription = "phone icon",
                modifier = Modifier.fillMaxSize().padding(16.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(AccentGreen)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        //endregion

        //region title and subtitle
        Text(
            text = "Notice Poster Registration",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Register with your institution details to post notices",
            style = MaterialTheme.typography.bodySmall,
            color = NeutralGray500
        )
        Spacer(modifier = Modifier.height(12.dp))
        //endregion

        //region full name text field
        Text(
            text = "Full Name",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = state.name,
            onValueChange = {
                if(it.length<=4)
                    onStateChange(state.copy(name = it))
            },
            placeholder = {
                Text(text = "Enter your full name", color = Color.Gray)
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = BorderGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )
        //endregion

        //region mobile number text field
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Mobile Number",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //phone number text field
        OutlinedTextField(
            value = state.mobileNumber,
            onValueChange = { newValue ->
                val filteredValue = newValue.filter { it.isDigit() }
                if (filteredValue.length <= 11) {
                    onStateChange(state.copy(mobileNumber = filteredValue))
                }
                /*if (filteredValue.length >= 11) {
                    sendCodeBtnEnable = true
                } else {
                    sendCodeBtnEnable = false
                }*/
            },
            placeholder = {
                Text(text = "18xxxxxxxxx", color = Color.Gray)
            },
            leadingIcon = {
                Row(
                    modifier = Modifier
                        .height(56.dp)
                        .background(
                            color = LightGray,
                            shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp)
                        )
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
                unfocusedIndicatorColor = BorderGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            //isError = isError,
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        //endregion

        //region institution type
        Text(
            text = "Institution Type",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            viewModel.institutionTypes().forEach { institute ->
                    FilterChip(
                        selected = state.instituteType == institute.label,
                        onClick = {
                            onAction(RegistrationScreenAction.NoticePoster.InstituteTypeSelect(institute.label))
                        },
                        label = {
                            Text(text = institute.label)
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = institute.icon,
                                contentDescription = institute.label,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        shape = RoundedCornerShape(16.dp),

                    )
            }
        }
        //endregion

        //region institution id
        if(state.instituteType.isNotEmpty()) {
            Text(
                text = "Institution ID",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = state.instituteType,
                onValueChange = {
                    if (it.length <= 4)
                        onStateChange(state.copy(name = it))
                },
                placeholder = {
                    Text(text = "Select institution id", color = Color.Gray)
                },
                modifier = Modifier.fillMaxWidth().clickable(
                    true,
                    onClick = {
                        onAction(RegistrationScreenAction.NoticePoster.InstituteDropDownClick)
                    }),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = BorderGray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "drop down icon",
                        modifier = Modifier.size(18.dp)
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        //endregion

        //region NID Verification
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(0.dp),
            colors = CardDefaults.cardColors(SurfaceBlue),
            border = BorderStroke(1.dp, BorderBlue)
        ){
            Row(modifier = Modifier.fillMaxSize().padding(12.dp)){
                Icon(
                    painter = painterResource(Res.drawable.shield),
                    contentDescription = "shield icon",
                    tint = AccentGreen,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "NID Verifivation Required",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Verify your National ID Card to post notices",
                        style = MaterialTheme.typography.bodySmall,
                        color = NeutralGray500
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onAction(RegistrationScreenAction.NoticePoster.NIDVerification)
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(40.dp),
                        enabled = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepGreen, // Make button background transparent
                        ),
                    ){
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "NID Verification",
                            modifier = Modifier.size(18.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verify via NID",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )

                    }
                }

            }
        }
        //endregion
        Spacer(modifier = Modifier.height(16.dp))
        //region continue button
        Button(
            onClick = {
                onAction(RegistrationScreenAction.NoticePoster.OnCompleteClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = state.instituteType.isNotEmpty(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DeepGreen, // Make button background transparent
                disabledContainerColor = Color(0xFF9CDCBC), // Make button background transparent
            ),
        ){
            Icon(
                painter = painterResource(Res.drawable.check_mark),
                contentDescription = "NID Verification",
                modifier = Modifier.size(18.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Complete Registration",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        //endregion
    }
}

