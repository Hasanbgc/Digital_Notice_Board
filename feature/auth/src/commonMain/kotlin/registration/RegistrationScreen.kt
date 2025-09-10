package registration

import AccentGreen
import BorderBlue
import BorderGray
import ErrorRed
import LightGray
import NeutralGray500
import SurfaceBackground
import SurfaceBlue
import TabBackgroundGray
import TrackColor
import androidx.collection.SimpleArrayMap
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
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
            viewModel.updateRegistrationState (
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
                // Wrap TabRow in a Box to control layering
                Spacer(modifier = Modifier.height(16.dp))
                Box {
                    // Background indicator layer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // Match TabRow height
                            .padding(6.dp)
                            .background(color = TabBackgroundGray, shape = RoundedCornerShape(32.dp))
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
                when (currentTab) {
                    AuthTab.NORMAL_USER -> RegisterNormalUser(
                        state = state,
                        viewModel = viewModel,
                        onStateChange = onStateChange,
                        onAction = onAction
                    )
                    AuthTab.NOTICE_POSTER -> RegisterNoticePoster()
                }
                Spacer(modifier = Modifier.height(16.dp))

            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "By registering you agree to our Terms of Service and Privacy Policy",
                style = MaterialTheme.typography.bodySmall,
                color = NeutralGray500,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

        }

    }


}


@Composable
fun RegisterNormalUser(
    state: RegistrationScreenState,
    viewModel: RegistrationViewModel,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit
){
    var currentStep by remember { mutableStateOf(0) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(2.dp).background(SurfaceBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val listOfState = listOf("Phone","Verify","Location")
        AnimatedProgressBar(
            steps = listOfState,
            currentStep =  state.progressState,
            trackColor = TrackColor,
            progressColor = AccentGreen
            )
        Spacer(modifier = Modifier.height(8.dp))

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
        }

    }

}

@Composable
fun PhoneVerification(
    state: RegistrationScreenState,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit
){
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
        Spacer(modifier = Modifier.height(12.dp))
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
                if(filteredValue.length >= 11){
                   sendCodeBtnEnable = true
                }else{
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
                        .background(color = LightGray,shape = RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done),
            isError = isError,
            shape = RoundedCornerShape(16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        //send code button
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

        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        brush = if(sendCodeBtnEnable)continueButtonBackgroundActive else continueButtonBackgroundInactive,
                        shape = RoundedCornerShape(16.dp)
                    )
                ,
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
                if(state.isLoading){
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
    }
}

@Composable
fun OTPCodeVerification(
    state: RegistrationScreenState,
    onStateChange: (RegistrationScreenState) -> Unit,
    onAction: (RegistrationScreenAction) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Text(
            text = "Enter Verification Code",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = SurfaceBlue, shape = RoundedCornerShape(16.dp))
                .border(width = 1.dp, color = BorderBlue, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
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
            //isError = state.error,

        )
        Spacer(modifier = Modifier.height(16.dp))
        //verify code button
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
                        brush = if(state.verifyButtonEnabled)continueButtonBackgroundActive else continueButtonBackgroundInactive,
                        shape = RoundedCornerShape(16.dp)
                    )
                ,
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
                if(state.isLoading){
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(12.dp))
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
                    modifier = Modifier.size(16.dp).clickable{
                        onAction(RegistrationScreenAction.NormalUser.onGoBackClick)
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Go Back",
                    style = MaterialTheme.typography.bodyMedium,
                    color = NeutralGray500,
                    modifier = Modifier.clickable{
                        onAction(RegistrationScreenAction.NormalUser.onGoBackClick)
                    }
                    )
            }

            Text(
                text = "Resend",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentGreen,
                modifier = Modifier.clickable{
                    onAction(RegistrationScreenAction.NormalUser.onResendClick)
                }
            )
        }

    }
}

@Composable
fun RegisterNoticePoster(){

}

