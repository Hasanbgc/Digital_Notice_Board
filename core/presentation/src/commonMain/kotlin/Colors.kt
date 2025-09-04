import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Light theme
val PrimaryGreenHover = Color(0xFF005A42)
val PrimaryGreenLight = Color(0xFF008B5C)
val BangladeshRed = Color(0xFFF42A41)
val CreamBackground = Color(0xFFFFF8E6)

// Secondary colors
val SuccessGreen = Color(0xFF10B981)
val SuccessGreenHover = Color(0xFF059669)
val WarningYellow = Color(0xFFF59E0B)
val InfoBlue50 = Color(0xFFEBF8FF)   // Tailwind blue-50
val InfoBlue200 = Color(0xFFBFDBFE)  // Tailwind blue-200

// Neutral colors
val CardBackground = Color(0xFFFFFFFF)
val PrimaryText = Color(0xFF1A1A1A)
val PrimaryTextAlt1 = Color(0xFF374151)
val PrimaryTextAlt2 = Color(0xFF4B5563)
val SecondaryText = Color(0xFF6B7280)
val SecondaryTextAlt = Color(0xFF9CA3AF)
val LightBorder = Color(0xFFE5E7EB) //boarder
val LightBorderAlt = Color(0xFFD1D5DB)
val LightBorderAlt2 = Color(0xFFF3F4F6)
val BackgroundMuted = Color(0xFFF9F9F6)
val BackgroundSecondary = Color(0xFFF5F5F0)
val PrimaryGreen = Color(0xFF006A4E)      // Deep Green
val SecondaryGreen = Color(0xFF2D8F5F)    // Mid Green
val TertiaryGreen = Color(0xFF3CB371)     // Light Green

val LightGray = Color(0xFFF7F9FA)         // Gray Light
val BorderGray = Color(0xFFE5E7EB)        // Border
val TabBackgroundGray = Color(0xFFF5F6F8) // Tab Background

val WelcomeTextGreen = Color(0xFF004F3B)  // Welcome Text
val SignInTextGreen = Color(0xFF2E9474)   // Sign In Text
val AccentGreen = Color(0xFF007A56)       // Green Text
val ErrorRed = Color(0xFFC10007)          // Red Text

val NeutralGray500 = Color(0xFF6D7585)    // Text Gray 500

val GreenSurface = Color(0xFFECFDF5)      // Green 400 (light surface)
val GreenBorderLight = Color(0xFFAEF0D0)  // Green Light Border

// Special green for Ramadan
val RamadanGreen = Color(0xFF2E7D32)

val splashScreenBackground = Brush.linearGradient(
    colors = listOf(PrimaryGreenLight, SecondaryGreen, TertiaryGreen),
    start = Offset(0f, 0f),
    end = Offset.Infinite
)
val loginBackground = Brush.linearGradient(
    colors = listOf(Color(0xFFF0FDF4), Color(0xFFD1FAE5)),
    start = Offset(0f, 0f),   // top-left
    end = Offset.Infinite     // bottom-right
)
val continueButtonBackgroundInactive = Brush.linearGradient(
    colors = listOf(Color(0xFF7CCCB4), Color(0xFF9CDCBC)),
    start = Offset(0f, 0f),   // top-left
    end = Offset.Infinite
)
val continueButtonBackgroundActive = Brush.linearGradient(
    colors = listOf(Color(0xFF04A054), Color(0xFF14AC4C)),
    start = Offset(0f, 0f),   // top-left
    end = Offset.Infinite
)
val signInButtonBackgroundInactive = Brush.linearGradient(
    colors = listOf(Color(0xFFF47CA4), Color(0xFFF47CB4),Color(0xFFF47C94)),
    start = Offset(0f, 0f),   // top-left
    end = Offset.Infinite
)
val signInButtonBackgroundActive = Brush.linearGradient(
    colors = listOf(Color(0xFFE40459), Color(0xFFE40430),Color(0xFFEC444C)),
    start = Offset(0f, 0f),   // top-left
    end = Offset.Infinite
)