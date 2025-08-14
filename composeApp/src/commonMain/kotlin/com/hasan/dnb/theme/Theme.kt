package com.hasan.dnb.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import md_theme_dark_background
import md_theme_dark_error
import md_theme_dark_errorContainer
import md_theme_dark_inverseOnSurface
import md_theme_dark_inversePrimary
import md_theme_dark_inverseSurface
import md_theme_dark_onBackground
import md_theme_dark_onError
import md_theme_dark_onErrorContainer
import md_theme_dark_onPrimary
import md_theme_dark_onPrimaryContainer
import md_theme_dark_onSecondary
import md_theme_dark_onSecondaryContainer
import md_theme_dark_onSurface
import md_theme_dark_onSurfaceVariant
import md_theme_dark_onTertiary
import md_theme_dark_onTertiaryContainer
import md_theme_dark_outline
import md_theme_dark_outlineVariant
import md_theme_dark_primary
import md_theme_dark_primaryContainer
import md_theme_dark_scrim
import md_theme_dark_secondary
import md_theme_dark_secondaryContainer
import md_theme_dark_surface
import md_theme_dark_surfaceTint
import md_theme_dark_surfaceVariant
import md_theme_dark_tertiary
import md_theme_dark_tertiaryContainer
import md_theme_light_background
import md_theme_light_error
import md_theme_light_errorContainer
import md_theme_light_inverseOnSurface
import md_theme_light_inversePrimary
import md_theme_light_inverseSurface
import md_theme_light_onBackground
import md_theme_light_onError
import md_theme_light_onErrorContainer
import md_theme_light_onPrimary
import md_theme_light_onPrimaryContainer
import md_theme_light_onSecondary
import md_theme_light_onSecondaryContainer
import md_theme_light_onSurface
import md_theme_light_onSurfaceVariant
import md_theme_light_onTertiary
import md_theme_light_onTertiaryContainer
import md_theme_light_outline
import md_theme_light_outlineVariant
import md_theme_light_primary
import md_theme_light_primaryContainer
import md_theme_light_scrim
import md_theme_light_secondary
import md_theme_light_secondaryContainer
import md_theme_light_surface
import md_theme_light_surfaceTint
import md_theme_light_surfaceVariant
import md_theme_light_tertiary
import md_theme_light_tertiaryContainer

// ========================
// MATERIAL 3 COLOR SCHEMES
// ========================

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

// =============================
// BANGLADESH-SPECIFIC TYPOGRAPHY
// =============================

@Composable
fun BangladeshTypography(): Typography {
    return Typography(
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
}

// =====================
// THEME IMPLEMENTATION
// =====================
@Composable
fun BangladeshTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isRamadan: Boolean = false, // Bangladesh-specific context
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*darkTheme && isRamadan -> DarkColorScheme.copy(primary = RamadanGreen)*/
        /* isRamadan -> LightColorScheme.copy(primary = RamadanGreen)*/
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = BangladeshTypography(),
        shapes = MaterialTheme.shapes,
        content = content
    )
}

/*
// ===========================
// EXTENSION FOR BANGLADESH UI
// ===========================
val MaterialTheme.bdColors: BangladeshColors
    @Composable get() = if (isLight) LightBangladeshColors else DarkBangladeshColors

data class BangladeshColors(
    val emergencyRed: Color,
    val mosqueGreen: Color,
    val schoolBlue: Color,
    val creamBackground: Color
)

private val LightBangladeshColors = BangladeshColors(
    emergencyRed = Color(0xFFF42A41),
    mosqueGreen = Color(0xFF2E7D32),
    schoolBlue = Color(0xFF1E88E5),
    creamBackground = Color(0xFFFFF8E6)
)

private val DarkBangladeshColors = BangladeshColors(
    emergencyRed = Color(0xFFFF5252),
    mosqueGreen = Color(0xFF388E3C),
    schoolBlue = Color(0xFF2196F3),
    creamBackground = Color(0xFF121212)
)*/
