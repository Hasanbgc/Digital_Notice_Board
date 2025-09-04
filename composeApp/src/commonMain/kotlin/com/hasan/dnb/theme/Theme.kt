package com.hasan.dnb.theme

import BorderGray
import ErrorRed
import GreenSurface
import LightGray
import NeutralGray500
import PrimaryGreen
import SecondaryGreen
import SignInTextGreen
import TabBackgroundGray
import TertiaryGreen
import WelcomeTextGreen
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = LightGray,

    secondary = SecondaryGreen,
    onSecondary = LightGray,

    tertiary = TertiaryGreen,
    onTertiary = LightGray,

    background = LightGray,
    onBackground = NeutralGray500,

    surface = GreenSurface,
    onSurface = WelcomeTextGreen,

    error = ErrorRed,
    onError = LightGray,

    outline = BorderGray,
)

private val DarkColors = darkColorScheme(
    primary = PrimaryGreen,
    onPrimary = LightGray,

    secondary = SecondaryGreen,
    onSecondary = LightGray,

    tertiary = TertiaryGreen,
    onTertiary = LightGray,

    background = NeutralGray500,
    onBackground = LightGray,

    surface = TabBackgroundGray,
    onSurface = SignInTextGreen,

    error = ErrorRed,
    onError = LightGray,

    outline = BorderGray,
)
@Composable
fun AppTypography()= Typography(
        displayLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W700,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp
        ),
        displayMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W700,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),
        displaySmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W700,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W600,
            fontSize = 32.sp,
            lineHeight = 40.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W600,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.W600,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        // Add more text styles as needed...
    )


@Composable
fun AppTheme(
    darkTheme: Boolean = false, // you can hook it up with system theme
    content: @Composable () -> Unit
) {
    val colors: ColorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography(), // define separately if needed
        shapes = MaterialTheme.shapes,         // define separately if needed
        content = content
    )
}
