package Profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import presentation.PrimaryText
import presentation.ScreenBg
import presentation.SecondaryText

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfileScreen(
        state,
        onAction = viewModel::onAction
    )

    if (state.showLogOutDialog) {
        LogOutDialog(
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun ProfileScreen(
    state: ProfileScreenState,
    onAction: (ProfileScreenAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBg)
            .verticalScroll(rememberScrollState())
    ) {
        // --- HEADER SECTION ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            AsyncImage(
                model = "https://picsum.photos/800/600",
                contentDescription = "Cover Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Black.copy(alpha = 0.4f))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                brush = Brush.sweepGradient(
                                    listOf(Color(0xFFD4AF37), Color(0xFFFFDF00), Color(0xFFD4AF37))
                                )
                            ),
                            shape = CircleShape
                        )
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF004D40))
                ) {
                    if (state.user.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = state.user.imageUrl,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {

                        Text(
                            text = state.user.name.take(1),
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.displayLarge.copy(
                                color = Color(0xFFFFDF00),
                                fontWeight = FontWeight.Bold,
                                fontSize = 60.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF004D40),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Business,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Institution",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.user.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = SecondaryText,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Dhaka, Bangladesh", color = SecondaryText, fontSize = 14.sp)
                Text(text = " • ", color = SecondaryText, fontSize = 14.sp)
                Text(text = "Joined Jan 2024", color = SecondaryText, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ContactPill(
                    icon = Icons.Default.Email,
                    text = state.user.email,
                    modifier = Modifier.weight(1f)
                )
                ContactPill(
                    icon = Icons.Default.Phone,
                    text = state.user.contactNumber,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- PERFORMANCE OVERVIEW SECTION ---
        SectionCard(
            title = "Performance Overview",
            actionText = "View Analytics",
            onActionClick = {}) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PerformanceItem(
                        icon = Icons.Default.Visibility,
                        value = "47",
                        label = "Notices",
                        modifier = Modifier.weight(1f),
                        iconTint = Color(0xFF10B981)
                    )
                    PerformanceItem(
                        icon = Icons.Default.Group,
                        value = "8.4K",
                        label = "Reach",
                        modifier = Modifier.weight(1f),
                        iconTint = Color(0xFF3B82F6)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PerformanceItem(
                        icon = Icons.AutoMirrored.Filled.TrendingUp,
                        value = "94",
                        label = "Impact",
                        modifier = Modifier.weight(1f),
                        iconTint = Color(0xFFEF4444)
                    )
                    PerformanceItem(
                        icon = Icons.Default.Favorite,
                        value = "2.8K",
                        label = "Followers",
                        modifier = Modifier.weight(1f),
                        iconTint = Color(0xFFF59E0B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- ACHIEVEMENTS SECTION ---
        SectionCard(title = "Achievements", actionText = "4 of 6", onActionClick = {}) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AchievementBadge(
                    icon = Icons.Default.Star,
                    tint = Color(0xFFEF4444),
                    isLocked = false
                )
                AchievementBadge(
                    icon = Icons.Default.Person,
                    tint = Color(0xFF3B82F6),
                    isLocked = false
                )
                AchievementBadge(
                    icon = Icons.Default.CheckCircle,
                    tint = Color(0xFF10B981),
                    isLocked = false
                )
                AchievementBadge(icon = Icons.Default.Whatshot, tint = Color.Gray, isLocked = true)
                AchievementBadge(
                    icon = Icons.Default.MilitaryTech,
                    tint = Color(0xFFF59E0B),
                    isLocked = false
                )
                AchievementBadge(
                    icon = Icons.Default.EmojiEvents,
                    tint = Color.Gray,
                    isLocked = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- RECENT ACTIVITY SECTION ---
        SectionCard(title = "Recent Activity", actionText = "View All", onActionClick = {}) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ActivityItem(
                    icon = Icons.Default.LocalPostOffice,
                    title = "Posted Emergency Weather Alert",
                    time = "2h",
                    iconBackground = Color(0xFFFEE2E2),
                    iconTint = Color(0xFFEF4444)
                )
                ActivityItem(
                    icon = Icons.Default.MilitaryTech,
                    title = "Earned Local Hero Badge",
                    time = "1d",
                    iconBackground = Color(0xFFD1FAE5),
                    iconTint = Color(0xFF10B981)
                )
                ActivityItem(
                    icon = Icons.Default.Group,
                    title = "Reached 1000+ People",
                    time = "3d",
                    iconBackground = Color(0xFFDBEAFE),
                    iconTint = Color(0xFF3B82F6)
                )
                ActivityItem(
                    icon = Icons.Default.Edit,
                    title = "Updated Profile Information",
                    time = "1w",
                    iconBackground = Color(0xFFFEF3C7),
                    iconTint = Color(0xFFF59E0B)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LogoutButton {
            onAction(ProfileScreenAction.OnLogoutClicked)
        }
        Spacer(modifier = Modifier.height(70.dp))

    }
}

@Composable
fun SectionCard(
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFF3B82F6).copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        val headerIcon = when (title) {
                            "Performance Overview" -> Icons.Default.Timeline
                            "Achievements" -> Icons.Default.EmojiEvents
                            "Recent Activity" -> Icons.Default.History
                            else -> Icons.Default.Star
                        }
                        Icon(
                            imageVector = headerIcon,
                            contentDescription = null,
                            tint = Color(0xFF3B82F6),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF3B82F6),
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.clickable { onActionClick() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun PerformanceItem(
    icon: ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    iconTint: Color
) {
    Box(
        modifier = modifier
            .border(1.dp, Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
            .background(Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(iconTint.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
            )
        }
    }
}

@Composable
fun AchievementBadge(
    icon: ImageVector,
    tint: Color,
    isLocked: Boolean
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .border(
                1.dp,
                if (isLocked) Color.LightGray else tint.copy(alpha = 0.5f),
                RoundedCornerShape(8.dp)
            )
            .background(
                if (isLocked) Color(0xFFF9FAFB) else tint.copy(alpha = 0.05f),
                RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isLocked) Color.LightGray else tint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun ActivityItem(
    icon: ImageVector,
    title: String,
    time: String,
    iconBackground: Color,
    iconTint: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(iconBackground, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText)
        )
    }
}

@Composable
fun ContactPill(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Color.Black.copy(alpha = 0.05f),
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SecondaryText,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = SecondaryText,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        state = ProfileScreenState(),
        onAction = {}
    )
}
