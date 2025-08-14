import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Light theme
val md_theme_light_primary = Color(0xFF006A4E)       // Bangladesh green
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFF7CF8CD)
val md_theme_light_onPrimaryContainer = Color(0xFF002116)
val md_theme_light_secondary = Color(0xFF4D6357)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFCFE9D7)
val md_theme_light_onSecondaryContainer = Color(0xFF0A1F15)
val md_theme_light_tertiary = Color(0xFF38656A)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFBCEBF0)
val md_theme_light_onTertiaryContainer = Color(0xFF002022)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFBFDF9)
val md_theme_light_onBackground = Color(0xFF191C1A)
val md_theme_light_surface = Color(0xFFFBFDF9)
val md_theme_light_onSurface = Color(0xFF191C1A)
val md_theme_light_surfaceVariant = Color(0xFFDBE5DD)
val md_theme_light_onSurfaceVariant = Color(0xFF404943)
val md_theme_light_outline = Color(0xFF707973)
val md_theme_light_inverseOnSurface = Color(0xFFEFF1ED)
val md_theme_light_inverseSurface = Color(0xFF2E312E)
val md_theme_light_inversePrimary = Color(0xFF006A4E)
val md_theme_light_surfaceTint = Color(0xFF006A4E)
val md_theme_light_outlineVariant = Color(0xFFBFC9C2)
val md_theme_light_scrim = Color(0xFF000000)
// Add more color tokens...

// Dark theme
val md_theme_dark_primary = Color(0xFF5CDBB1)
val md_theme_dark_onPrimary = Color(0xFF003828)
val md_theme_dark_primaryContainer = Color(0xFF00513B)
val md_theme_dark_onPrimaryContainer = Color(0xFF7CF8CD)
val md_theme_dark_secondary = Color(0xFFB3CCBC)
val md_theme_dark_onSecondary = Color(0xFF1F3529)
val md_theme_dark_secondaryContainer = Color(0xFF364B3F)
val md_theme_dark_onSecondaryContainer = Color(0xFFCFE9D7)

val md_theme_dark_tertiary = Color(0xFF4FD8EB)
val md_theme_dark_onTertiary = Color(0xFF00363D)
val md_theme_dark_tertiaryContainer = Color(0xFF004F59)
val md_theme_dark_onTertiaryContainer = Color(0xFF97F0FF)

val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)

val md_theme_dark_background = Color(0xFF1C1B1F)
val md_theme_dark_onBackground = Color(0xFFE6E1E5)

val md_theme_dark_surface = Color(0xFF1C1B1F)
val md_theme_dark_onSurface = Color(0xFFE6E1E5)

val md_theme_dark_surfaceVariant = Color(0xFF49454F)
val md_theme_dark_onSurfaceVariant = Color(0xFFCAC4D0)

val md_theme_dark_outline = Color(0xFF938F99)
val md_theme_dark_inverseOnSurface = Color(0xFF1C1B1F)
val md_theme_dark_inverseSurface = Color(0xFFE6E1E5)
val md_theme_dark_inversePrimary = Color(0xFF6750A4)

val md_theme_dark_surfaceTint = Color(0xFFD0BCFF)
val md_theme_dark_outlineVariant = Color(0xFF49454F)
val md_theme_dark_scrim = Color(0xFF000000)
val deep_green = Color(0xFF006A4E)
val mid_green = Color(0xFF2d8f5f)
val light_green = Color(0xFF3cb371)
val gray_light = Color(0xFFf7f9fa)
val boarder = Color(0xFFe5e7eb)

val tabBackground = Color(0xFFf5f6f8)

val welcomeTextColor = Color(0xFF004f3b)
val signInTextColor = Color(0xFF2e9474)
val greenTextColor = Color(0xFF007a56)
val redTextColor = Color(0xFFc10007)

// Add more color tokens...

// Bangladesh-specific colors
val RamadanGreen = Color(0xFF2E7D32)

// Special green for Ramadan

val splashScreenBackground = Brush.linearGradient(
    colors = listOf(md_theme_light_primary, mid_green, light_green),
    start = Offset(0f, 0f),
    end = Offset.Infinite
)
val loginBackground = Brush.linearGradient(
    colors = listOf(Color(0xFFF0FDF4), Color(0xFFD1FAE5)),
    start = Offset(0f, 0f),   // top-left
    end = Offset.Infinite     // bottom-right
)