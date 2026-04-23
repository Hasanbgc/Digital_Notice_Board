package AuthScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hasan.dnb.firebaseLog
import digita_notice_board.feature.auth.generated.resources.Res
import digita_notice_board.feature.auth.generated.resources.facebook
import digita_notice_board.feature.auth.generated.resources.google
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.AppDestination
import presentation.UiEvent


@Composable
fun AuthScreenRoot(
    authViewModel: AuthViewModel,
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val state by authViewModel.authScreenState.collectAsStateWithLifecycle()

    AuthScreen(state, authViewModel::onAction)

    LaunchedEffect(Unit) {
        authViewModel.events.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> onBack()
                is UiEvent.Navigate -> {
                    onLoginSuccess()

                }

                is UiEvent.ShowSnackbar -> {}
                is UiEvent.ShowToast -> {}
            }
        }
    }
    LaunchedEffect(Unit) {
        firebaseLog("AuthScreenRoot")
    }
}

@Composable
fun AuthScreen(
    state: AuthScreenState,
    onAction: (AuthScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ── Header ─────────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color(0xFFDAE8E5)),
            contentAlignment = Alignment.Center
        ) {
            // App logo placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color(0xFF00897B),
                        shape = RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "DNB",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ── Content ────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Welcome to DNB",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Continue to get started",
                fontSize = 15.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Google button
            SocialButton(
                text = "Continue with Google",
                isLoading = state.isGoogleLoading,
                onClick = { onAction(AuthScreenAction.OnGoogleClick) },
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.google),
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Facebook button
            SocialButton(
                text = "Continue with Facebook",
                isLoading = state.isFacebookLoading,
                onClick = { onAction(AuthScreenAction.OnFacebookClick) },
                icon = {
                    Icon(
                        painter = painterResource(Res.drawable.facebook),
                        contentDescription = "facebook",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Error banner
            state.errorMessage?.let {
                ErrorBanner(message = it)
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Terms text
            TermsText(
                onTermsClick = { onAction(AuthScreenAction.OnTermsClick) },
                onPrivacyClick = { onAction(AuthScreenAction.OnPrivacyClick) }
            )
        }
    }
}

// ── Social button ──────────────────────────────────────────────────────────
@Composable
private fun SocialButton(
    text: String,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = Color.Gray
            )
        } else {
            icon()
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ── Error banner ───────────────────────────────────────────────────────────
@Composable
private fun ErrorBanner(message: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFF3CD),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        // TODO: Place warning icon here
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(Color(0xFFE8A317), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("!", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }

        Column {
            Text(
                text = "Sign-in failed",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color(0xFF664D03)
            )
            Text(
                text = message,
                fontSize = 13.sp,
                color = Color(0xFF997A0A)
            )
        }
    }
}

// ── Terms text ─────────────────────────────────────────────────────────────
@Composable
private fun TermsText(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    val annotatedString = buildAnnotatedString {
        append("By continuing, you agree to our ")
        pushStringAnnotation(tag = "terms", annotation = "terms")
        withStyle(SpanStyle(color = Color(0xFF00897B), fontWeight = FontWeight.Medium)) {
            append("Terms of Service")
        }
        pop()
        append(" and ")
        pushStringAnnotation(tag = "privacy", annotation = "privacy")
        withStyle(SpanStyle(color = Color(0xFF00897B), fontWeight = FontWeight.Medium)) {
            append("Privacy Policy")
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(fontSize = 13.sp, color = Color.Gray, textAlign = TextAlign.Center),
        modifier = Modifier.fillMaxWidth(),
        onClick = { offset ->
            annotatedString.getStringAnnotations("terms", offset, offset)
                .firstOrNull()?.let { onTermsClick() }
            annotatedString.getStringAnnotations("privacy", offset, offset)
                .firstOrNull()?.let { onPrivacyClick() }
        }
    )
}

// ── Preview ────────────────────────────────────────────────────────────────
@Preview()
@Composable
private fun AuthScreenDefaultPreview() {
    AuthScreen(
        state = AuthScreenState(),
        onAction = {}
    )
}

@Preview()
@Composable
private fun AuthScreenLoadingPreview() {
    AuthScreen(
        state = AuthScreenState(isGoogleLoading = true),
        onAction = {}
    )
}

@Preview()
@Composable
private fun AuthScreenErrorPreview() {
    AuthScreen(
        state = AuthScreenState(errorMessage = "Something went wrong. Please try again."),
        onAction = {}
    )
}