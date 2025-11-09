package home

import AccentGreen
import EmergenceyAlertRedBG
import EmergencyIconBG
import ErrorRed
import FileCardGradiant
import NeonEffect
import PrimaryText
import PrimaryTextAlt2
import ShareButtonGradiant
import ViolateGradiant
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.PlatformContext
import coil3.compose.AsyncImage
import cornerStretchAnimation
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.fire_outlined
import digita_notice_board.feature.noticeboard.generated.resources.flood
import digita_notice_board.feature.noticeboard.generated.resources.load_shedding
import digita_notice_board.feature.noticeboard.generated.resources.road_construction
import digita_notice_board.feature.noticeboard.generated.resources.warning
import home.component.CurvedCornerTriangle
import home.component.ProfileImageWithPlaceholder
import home.component.WaveFilledShape
import io.ktor.client.request.invoke
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreenRoot(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        paddingValues = paddingValues,
        state = state,
        onNavigateToDetail = onNavigateToDetail
    )

}

//@Preview
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    state: HomeScreenState,
    onNavigateToDetail: (String) -> Unit
) {
    // UI code
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Notice Board",
                modifier = Modifier
                    .align(Alignment.Start),
                style = TextStyle(
                    brush = ViolateGradiant,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )

            )
            Text(
                text = "Stay updated with important notices",
                modifier = Modifier
                    .align(Alignment.Start),
                color = PrimaryTextAlt2,
                fontSize = 12.sp
            )

        }

        Spacer(modifier = Modifier.height(8.dp))
        if (state.poster.isEmpty()) {  //temporary only test purpose, it must be isNotEmpty()
            var emergencyNoticeCount = state.poster.count { it is Poster.Emergency }
            emergencyNoticeCount = 2  //temporary only test purpose, it should be deleted
            if (emergencyNoticeCount > 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = EmergenceyAlertRedBG)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically

                )
                {
                    Icon(
                        painter = painterResource(Res.drawable.warning),
                        contentDescription = "warning",
                        modifier = Modifier.size(20.dp).cornerStretchAnimation(),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Emergency Alerts",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "$emergencyNoticeCount active emergencies in your area",
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "right_arrow",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

        }
        /*Spacer(modifier = Modifier.height(8.dp))

        EmergencyNotice(
            poster = Poster.Emergency(
                0,
                "Flash Flood Warning",
                "Heavy rainfall causing flood in low-laying areas. lorem ipsum dolor sit amet, consectetur adipiscing elit.  ",
                "5/11/2025",
                "2km away",
                "5 min ago",
                "",
                "Dhanmondi Area",
                Type.MEDIUM,
                Topic.FIRE
            ), onNavigateToDetail = onNavigateToDetail
        )
        Spacer(modifier = Modifier.height(8.dp))*/
        NormalNotice(
            poster = Poster.Normal(
                0,
                "University Admission Test Result Published",
                description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
                date = "5/11/2025",
                distance = "2km away",
                time = "5 min ago",
                imageUrlList = listOf(
                    "https://picsum.photos/id/10/400/300",
                    "https://picsum.photos/id/20/400/300",
                    "https://picsum.photos/id/30/400/300",
                ),
                location = "Dhanmondi Area",
                type = Type.URGENT,
                profile = Profile(
                    name = "John Doe",
                    imageUrl = "https://picsum.photos/id/10/400/300",
                    institution = "Chittagong University",
                    designation = "Student"
                ),
                attachments = listOf("Job_description.pdf", "Application_Form.docx"),
                isFavorite = false,
                shareCount = 23,
                commentCount = 45,
                likeCount = 123,
                isSaved = false,
                viewCount = 1250,
                isExpanded = false
            ),
            onNavigateToDetail = onNavigateToDetail
        )


        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp)
        )
        {
            items(
                items = state.poster,
            ) { notice ->
                when (notice) {
                    is Poster.Emergency -> {
                        EmergencyNotice(
                            poster = notice,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }

                    is Poster.Normal -> {
                        NormalNotice(
                            poster = notice,
                            onNavigateToDetail = onNavigateToDetail
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun EmergencyNotice(
    poster: Poster.Emergency,
    onNavigateToDetail: (String) -> Unit
)
{

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 500))
            .NeonEffect()
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clickable {
                poster.isExpanded = !poster.isExpanded
            }
            .padding(horizontal = 10.dp, vertical = 14.dp),
    )
    {

        Image(
            painter = getIcon(poster),
            contentDescription = "emergency",
            modifier = Modifier
                .size(30.dp)
                .clip(shape = CircleShape)
                .background(color = EmergencyIconBG)
                .padding(8.dp)

        )

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = poster.title,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = poster.description,
                fontSize = 12.sp,
                color = PrimaryTextAlt2,
                maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "location",
                        modifier = Modifier.size(12.dp),
                        tint = PrimaryTextAlt2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${poster.distance}, ${poster.location}",
                        fontSize = 12.sp,
                        color = PrimaryTextAlt2
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = "location",
                        modifier = Modifier.size(12.dp),
                        tint = PrimaryTextAlt2
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = poster.time,
                        fontSize = 12.sp,
                        color = PrimaryTextAlt2
                    )
                }
            }
        }

        //Spacer(modifier = Modifier.weight(1f))

        Card(
            Modifier.wrapContentSize(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = EmergencyIconBG)
        )
        {
            Text(
                text = poster.type.name,
                fontSize = 12.sp,
                color = ErrorRed,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            )
        }

    }
}

@Composable
fun NormalNotice(
    poster: Poster.Normal,
    onNavigateToDetail: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 500.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .clickable { isExpanded = !isExpanded})
        {

            WaveFilledShape(
                Modifier.size(40.dp).align(Alignment.TopEnd),
                colors = listOf(Color.Green,AccentGreen)
            )
            Column(modifier = Modifier
                .fillMaxWidth().padding(12.dp).align(Alignment.TopStart))
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ProfileImageWithPlaceholder(
                        modifier = Modifier.size(46.dp),
                        imageUrl = poster.profile.imageUrl,
                        institutionName = poster.profile.institution,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.wrapContentSize(
                            align = Alignment.TopStart,
                            unbounded = false
                        )
                    ) {
                        Text(
                            text = poster.profile.name,
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = PrimaryText,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = poster.profile.designation,
                            fontSize = 12.sp,
                            color = PrimaryTextAlt2
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = poster.title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = poster.description,
                    fontSize = 12.sp,
                    color = PrimaryTextAlt2,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))
                if (poster.imageUrlList.isNotEmpty()) {
                    when (poster.imageUrlList.size) {
                        1 -> {
                            Card(modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                                shape = RoundedCornerShape(8.dp),
                            ) {
                                AsyncImage(
                                    model = poster.imageUrlList[0],
                                    contentDescription = "image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        else -> {
                            Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Card(modifier = Modifier
                                    .weight(1f)
                                    .height(100.dp),
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    AsyncImage(
                                        model = poster.imageUrlList[0],
                                        contentDescription = "image",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                Card(modifier = Modifier
                                        .weight(1f)
                                    .height(100.dp),
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        AsyncImage(
                                            model = poster.imageUrlList[1],
                                            contentDescription = "image",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                        if(poster.imageUrlList.size > 2) {
                                            Box(
                                                modifier = Modifier.fillMaxSize()
                                                    .background(color = Color.Black.copy(alpha = 0.6f)),
                                                contentAlignment = Alignment.Center,
                                            ) {
                                                Text(
                                                    text = "+${poster.imageUrlList.size - 2}",
                                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                for (attachment in poster.attachments) {
                    Card(
                        elevation = CardDefaults.cardElevation(1.dp),
                        border = BorderStroke(0.5.dp, Color.LightGray),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .background(brush = FileCardGradiant)
                            .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                imageVector = Icons.Default.AttachFile,
                                contentDescription = "File",
                                modifier = Modifier
                                    .background(brush = ShareButtonGradiant, shape = RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = attachment,
                                modifier = Modifier.padding()
                            )

                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Outlined.FileDownload,
                                contentDescription = "Close",
                                tint = Color.Blue
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                //Spacer(modifier = Modifier.height(14.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(brush = FileCardGradiant)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                )
            {
                    Card(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize().padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Icon(
                                imageVector = Icons.Outlined.HeartBroken,
                                contentDescription = "like",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "123",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
            }
            Spacer(modifier = Modifier.height(12.dp))

        }
    }
}


@Composable
fun getIcon(poster: Poster.Emergency) = when (poster.topic) {
    Topic.FIRE -> painterResource(Res.drawable.fire_outlined)
    Topic.FLOOD -> painterResource(Res.drawable.flood)
    Topic.LOAD_SHEDDING -> painterResource(Res.drawable.load_shedding)
    Topic.ROAD_CONSTRUCTION -> painterResource(Res.drawable.road_construction)
    Topic.EARTHQUAKE -> TODO()
    Topic.GAS_LEAK -> TODO()
    Topic.ACCIDENT -> TODO()
    Topic.STORM -> TODO()
    Topic.CYCLONE -> TODO()
    Topic.WATER_SUPPLY_DISRUPTION -> TODO()
    Topic.ELECTRICITY_OUTAGE -> TODO()
    Topic.TRAFFIC_JAM -> TODO()
    Topic.POLICE_ALERT -> TODO()
    Topic.MISSING_PERSON -> TODO()
    Topic.HEALTH_ALERT -> TODO()
    Topic.WEATHER_WARNING -> TODO()
}