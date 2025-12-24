package home

import AccentGreen
import ButtonCardGradiant
import EmergenceyAlertRedBG
import EmergencyIconBG
import ErrorRed
import FileCardGradiant
import KottieAnimation
import NeonEffect
import PrimaryBlue
import PrimaryText
import PrimaryTextAlt2
import ShareButtonGradiant
import ViolateGradiant
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.HeartBroken
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import cornerStretchAnimation
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.chat
import digita_notice_board.feature.noticeboard.generated.resources.document
import digita_notice_board.feature.noticeboard.generated.resources.fire_outlined
import digita_notice_board.feature.noticeboard.generated.resources.flag
import digita_notice_board.feature.noticeboard.generated.resources.flood
import digita_notice_board.feature.noticeboard.generated.resources.heart
import digita_notice_board.feature.noticeboard.generated.resources.load_shedding
import digita_notice_board.feature.noticeboard.generated.resources.road_construction
import digita_notice_board.feature.noticeboard.generated.resources.share
import digita_notice_board.feature.noticeboard.generated.resources.warning
import home.component.CurvedCornerTriangle
import home.component.FloatingAddButton
import home.component.ProfileImageWithPlaceholder
import home.component.WaveFilledShape
import kottieAnimationState.KottieAnimationState
import kottieComposition.KottieCompositionResult
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import utils.KottieConstants

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
        onAction = viewModel::onAction,
        onNavigateToDetail = onNavigateToDetail
    )

}


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    state: HomeScreenState,
    onAction: (HomeScreenAction) -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingAddButton(
                modifier = Modifier.size(70.dp),
            ){
                onAction(HomeScreenAction.PostANoticeClicked)
            }
        }
    ) {

        // UI code
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    modifier = Modifier.wrapContentHeight().padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
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
                Row(
                    modifier = Modifier.wrapContentHeight().padding(8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if(state.emergencyAlertClosed) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "notification",
                            tint = EmergenceyAlertRedBG,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable(
                                    onClick = { onAction(HomeScreenAction.OnNotificationClicked) }
                                )
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(8.dp))
            if (state.poster.isNotEmpty() && !state.emergencyAlertClosed) {
                val emergencyNoticeCount = state.poster.count { it is Poster.Emergency }

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
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(
                                    onClick = {onAction(HomeScreenAction.OnEmergencyAlertDismiss)}
                                )
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            )
            {
                val emergencyNotice = state.poster.filterIsInstance<Poster.Emergency>()
                val normalNotice = state.poster.filterIsInstance<Poster.Normal>()

                if(!state.emergencyAlertClosed) {
                items(
                    items = emergencyNotice,
                    key = { notice ->
                        notice.id.also {
                            println("key e: $it")
                        }
                    }
                ) { notice ->

                        EmergencyNotice(
                            poster = notice,
                            onNavigateToDetail = onNavigateToDetail
                        )

                }

                item {
                    Column(modifier = Modifier.padding(top = 12.dp, bottom = 10.dp)) {
                        Text(
                            text = "Personalized Feed",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Notices tailored to your interests and location",
                            fontSize = 12.sp,
                            color = PrimaryTextAlt2
                        )
                    }
                }
                }
                items(
                    items = normalNotice,
                    key = { notice ->
                        notice.id.also {
                            println("key: $it")
                        }
                    }
                ) { notice ->
                    NormalNotice(
                        poster = notice,
                        onAction = onAction,
                        onNavigateToDetail = onNavigateToDetail
                    )
                }
            }
        }
    }
}


@Composable
fun EmergencyNotice(
    poster: Poster.Emergency,
    onNavigateToDetail: (String) -> Unit
) {
    var animation by remember { mutableStateOf("") }
    val icon = getIcon(poster)
    LaunchedEffect(Unit) {
        animation = Res.readBytes(icon).decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = KottieConstants.IterateForever
    )
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

        KottieAnimation(
            composition = composition,
            modifier = Modifier.size(30.dp)
                .clip(shape = CircleShape)
                .background(color = EmergencyIconBG)
                .padding(4.dp),
            progress = { animationState.progress }
        )

        Column(modifier = Modifier.padding(start = 8.dp)) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Text(
                    text = poster.title,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f),
                    maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
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
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = poster.description,
                fontSize = 12.sp,
                color = PrimaryTextAlt2,
                maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.weight(0.7f),
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
                        color = PrimaryTextAlt2,
                        maxLines = if (poster.isExpanded) Int.MAX_VALUE else 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = "timer",
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
    }
}

@Composable
fun NormalNotice(
    poster: Poster.Normal,
    onAction: (HomeScreenAction) -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showSeeMore by remember { mutableStateOf(false) }
    var toggleLiked by remember { mutableStateOf(false) }
    val animationLike = getAnimation("files/love.json", 1)
    //val animationUnlike = getAnimation("file/heart.json",1)


    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(durationMillis = 300))
        ) {

            WaveFilledShape(
                Modifier.size(40.dp).align(Alignment.TopEnd),
                colors = listOf(Color.Green, AccentGreen)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {


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
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.clickable { isExpanded = !isExpanded },
                        onTextLayout = {
                            if (it.hasVisualOverflow) {
                                showSeeMore = true
                            }
                        }
                    )
                    if (showSeeMore && !isExpanded) {
                        Text(
                            text = "see more",
                            color = Color.Blue,
                            modifier = Modifier.clickable { isExpanded = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    if (poster.imageUrlList.isNotEmpty()) {
                        when (poster.imageUrlList.size) {
                            1 -> {
                                Card(
                                    modifier = Modifier
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
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Card(
                                        modifier = Modifier
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

                                    Card(
                                        modifier = Modifier
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
                                            if (poster.imageUrlList.size > 2) {
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
                    //for (attachment in poster.attachments) {
                    for (attachment in poster.attachments) {
                        AttachmentCard(attachment = attachment)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(brush = ButtonCardGradiant)
                        .padding(16.dp)
                )
                {
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable(onClick = {
                                toggleLiked = !toggleLiked
                                onAction(HomeScreenAction.OnLikeClicked(poster.id, toggleLiked)) }
                            ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            when(poster.liked) {
                                Like.IDLE -> {
                                    Icon(
                                        painter = painterResource(Res.drawable.heart),
                                        contentDescription = "like",
                                        modifier = Modifier.size(14.dp),
                                        tint = Color.Black
                                    )
                                }
                                Like.LIKED -> {
                                    val animationLike = getAnimation("files/love.json", 1)
                                    KottieAnimation(
                                        composition = animationLike.first,
                                        modifier = Modifier.size(14.dp).graphicsLayer{
                                            scaleX = 5.4f
                                            scaleY = 5.9f
                                        },
                                        progress = { animationLike.second.progress }
                                    )
                                }
                                Like.UNLIKED -> {
                                    Icon(
                                        painter = painterResource(Res.drawable.heart),
                                        contentDescription = "like",
                                        modifier = Modifier.size(14.dp),
                                        tint = Color.Black
                                    )
                                }

                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "123",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    Card(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.share),
                                contentDescription = "shared",
                                modifier = Modifier.size(14.dp),
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
                    Spacer(modifier = Modifier.width(12.dp))

                    Card(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.chat),
                                contentDescription = "comments",
                                modifier = Modifier.size(15.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "45",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(Res.drawable.flag),
                        contentDescription = "right_arrow",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                            .align(Alignment.CenterVertically),
                    )
                }
            }

        }
    }
}

@Composable
fun AttachmentCard(
    attachment: String
) {
    Card(
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = FileCardGradiant)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.document),
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
                tint = PrimaryBlue
            )
        }
    }
}


@Composable
fun getIcon(poster: Poster.Emergency): String {
    return when (poster.topic) {
        Topic.FIRE -> "files/fire.json"
        Topic.FLOOD -> TODO() // painterResource(Res.drawable.flood)
        Topic.LOAD_SHEDDING -> TODO() // painterResource(Res.drawable.load_shedding)
        Topic.ROAD_CONSTRUCTION -> TODO() // painterResource(Res.drawable.road_construction)
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
}

@Composable
fun getAnimation(
    filePath: String,
    iterations: Int = 1,
    reverse: Boolean = false
): Pair<KottieCompositionResult, KottieAnimationState> {
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes(filePath).decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        reverseOnRepeat = reverse
    )
    return Pair(composition, animationState)
}

//@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            paddingValues = PaddingValues(0.dp),
            state = HomeScreenState(
                isLoading = false,
                poster = listOf(
                    Poster.Normal(
                        0,
                        "University Admission Test Result Published",
                        description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!,Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
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
                    )
                ),
                error = null,
                isEmergencyPosterExpanded = false
            ),
            onAction = {},
            onNavigateToDetail = { },
        )
    }
}

@Preview
@Composable
fun NormalPosterPreview() {
    NormalNotice(
        Poster.Normal(
            0,
            "University Admission Test Result Published",
            description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!,Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
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
            attachments = listOf(
                "Job_description.pdf",
                "Application_Form.docx",
                "Application_Form.docx"
            ),
            isFavorite = false,
            shareCount = 23,
            commentCount = 45,
            likeCount = 123,
            isSaved = false,
            viewCount = 1250,
            isExpanded = false
        ),
        onAction = { },
        onNavigateToDetail = { }
    )
}

@Preview
@Composable
fun EmergencyPosterPreview() {
    EmergencyNotice(
        poster = Poster.Emergency(
            0,
            "Flash Flood Warning, Please Stay Away From There",
            "Heavy rainfall causing flood in low-laying areas. lorem ipsum dolor sit amet, consectetur adipiscing elit.  ",
            "5/11/2025",
            "2km away",
            "5 min ago",
            "",
            "Dhanmondi Area, Near Dhaka University",
            Type.MEDIUM,
            Topic.FIRE
        ),
        onNavigateToDetail = { }
    )
}


