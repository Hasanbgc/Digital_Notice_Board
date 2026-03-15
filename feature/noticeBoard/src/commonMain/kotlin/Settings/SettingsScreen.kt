package Settings

import Settings.SettingsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import presentation.BorderColor
import presentation.CardBg
import presentation.PrimaryGreen
import presentation.RedColor
import presentation.ScreenBg
import presentation.TextPrimary
import presentation.TextSecondary

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onBackPressed: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

        /*SettingsScreen(
            // state = state,
            onBackPressed = onBackPressed,
            viewModel
        )*/
    /*CoroutineScope(Dispatchers.Main).launch {
        runBasicFlow(viewModel)
    }*/


    SettingsScreen(
        state = state,
        onEvent = viewModel::OnEvent
    )
}
/*@Composable
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
}*/
@Composable
fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // ---------------- Account ----------------

        item {
            SettingsSection(title = "Account") {

                SettingsItem(
                    icon = Icons.Default.Person,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Edit Profile",
                    subtitle = "Update your personal information",
                    onClick = { onEvent(SettingsEvent.EditProfileClicked) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                SettingsItem(
                    icon = Icons.Default.Lock,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Change PIN",
                    subtitle = "Update your security PIN",
                    onClick = { onEvent(SettingsEvent.ChangePinClicked) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                SettingsItem(
                    icon = Icons.Default.LocationOn,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Location Preferences",
                    subtitle = "Set your area and delivery radius",
                    onClick = { onEvent(SettingsEvent.LocationClicked) }
                )
            }
        }

        // ---------------- Notifications ----------------

        item {
            SettingsSection(title = "Notifications") {

                ToggleItem(
                    icon = Icons.Default.Sms,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "SMS Notifications",
                    subtitle = "Receive alerts via SMS",
                    checked = state.smsEnabled,
                    onCheckedChange = {
                        onEvent(SettingsEvent.ToggleSms(it))
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                ToggleItem(
                    icon = Icons.Default.Call,
                    iconBg = Color(0xFFFFEAEA),
                    iconTint = RedColor,
                    title = "Voice Alerts",
                    subtitle = "Voice alerts for urgent notices",
                    checked = state.voiceAlertsEnabled,
                    onCheckedChange = {
                        onEvent(SettingsEvent.ToggleVoice(it))
                    }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                ToggleItem(
                    icon = Icons.Default.Notifications,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Push Notifications",
                    subtitle = "In-app notifications",
                    checked = state.pushEnabled,
                    onCheckedChange = {
                        onEvent(SettingsEvent.TogglePush(it))
                    }
                )
            }
        }

        // ---------------- Preferences ----------------

        item {
            SettingsSection(title = "Preferences") {

                SettingsItem(
                    icon = Icons.Default.Category,
                    iconBg = Color(0xFFF3E8FF),
                    iconTint = Color(0xFF7C3AED),
                    title = "Interest Categories",
                    subtitle = "Customize your notice feed",
                    onClick = { onEvent(SettingsEvent.InterestCategoriesClicked) }
                )
            }
        }

        // ---------------- Language ----------------

        item {
            SettingsSection(title = "Language") {

                LanguageItem(
                    title = "English",
                    subtitle = "Primary language",
                    selected = state.selectedLanguage == Language.ENGLISH,
                    onClick = {
                        onEvent(SettingsEvent.LanguageSelected(Language.ENGLISH))
                    }
                )

                LanguageItem(
                    title = "বাংলা",
                    subtitle = "বাংলাদেশ",
                    selected = state.selectedLanguage == Language.BANGLA,
                    onClick = {
                        onEvent(SettingsEvent.LanguageSelected(Language.BANGLA))
                    }
                )
            }
        }

        // ---------------- Privacy ----------------

        item {
            SettingsSection(title = "Privacy & Security") {

                ToggleItem(
                    icon = Icons.Default.Visibility,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Profile Visibility",
                    subtitle = "Everyone can see",
                    checked = state.profileVisibility,
                    onCheckedChange = {
                        onEvent(SettingsEvent.ToggleProfileVisibility(it))
                    }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                SettingsItem(
                    icon = Icons.Outlined.Shield,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Privacy Policy",
                    subtitle = "How we protect your data",
                    onClick = { onEvent(SettingsEvent.PrivacyPolicyClicked) }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                SettingsItem(
                    icon = Icons.Outlined.Description,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Terms of Service",
                    subtitle = "User agreement and guidelines",
                    onClick = { onEvent(SettingsEvent.TermsClicked) }
                )
            }
        }

        // ---------------- Help ----------------

        item {
            SettingsSection(title = "Help & Support") {

                SettingsItem(
                    icon = Icons.Outlined.HelpOutline,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Help Center",
                    subtitle = "FAQs and guides",
                    onClick = { onEvent(SettingsEvent.HelpCenterClicked) }
                )

                HorizontalDivider(
                    modifier = Modifier.padding(start = 69.dp, end = 20.dp),
                    thickness = 0.5.dp,
                    color = Color(0xFFE0E0E0)
                )

                SettingsItem(
                    icon = Icons.Outlined.Email,
                    iconBg = Color(0xFFE8F3F0),
                    iconTint = PrimaryGreen,
                    title = "Contact Support",
                    subtitle = "Get help from our team",
                    onClick = { onEvent(SettingsEvent.ContactSupportClicked) }
                )
            }
        }

        // ---------------- Logout ----------------

        item {
            LogoutButton {
                onEvent(SettingsEvent.LogoutClicked)
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {

        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(CardBg)
                .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                .padding(vertical = 8.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconTint)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, color = TextPrimary)
            Text(
                subtitle,
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = TextSecondary
        )
    }
}
@Composable
fun ToggleItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = iconTint)
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, color = TextPrimary)
            Text(subtitle, fontSize = 12.sp, color = TextSecondary)
        }

        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange?.invoke(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = PrimaryGreen,
                uncheckedTrackColor = Color(0xFFE0E0E0)
            )
        )
    }
}

@Composable
fun LanguageItem(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) Color(0xFFE8F3F0) else Color.Transparent
            )
            .border(
                width = if (selected) 1.dp else 0.dp,
                color = if (selected) PrimaryGreen else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 14.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, color = TextPrimary)
            Text(subtitle, fontSize = 12.sp, color = TextSecondary)
        }

        if (selected) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = PrimaryGreen
            )
        }
    }
}

@Composable
fun LogoutButton(
    onLogoutClick: () -> Unit
) {
    Button(
        onClick = onLogoutClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF1E2D)
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Logout,
            contentDescription = null,
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Logout from Account",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
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

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(state = SettingsState(), onEvent = {})
}