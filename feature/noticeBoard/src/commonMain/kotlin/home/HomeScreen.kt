package home

import KottieAnimation
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.chat
import digita_notice_board.feature.noticeboard.generated.resources.document
import digita_notice_board.feature.noticeboard.generated.resources.flag
import digita_notice_board.feature.noticeboard.generated.resources.flaged
import digita_notice_board.feature.noticeboard.generated.resources.heart
import digita_notice_board.feature.noticeboard.generated.resources.share
import digita_notice_board.feature.noticeboard.generated.resources.warning
import home.component.CustomSearchBar
import home.component.EmergencyCarousel
import home.component.EmergencyNotice
import home.component.HeaderSection
import home.component.NormalNotice
import home.component.ProfileImageWithPlaceholder
import home.component.RoundGradientButton
import home.dialog.ImageFullScreenDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kottieAnimationState.KottieAnimationState
import kottieComposition.KottieCompositionResult
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import likeAnimationSize
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import presentation.BorderGray
import presentation.ButtonCardGradiant
import presentation.EmergenceyAlertRedBG
import presentation.EmergencyGradient
import presentation.EmergencyIconBG
import presentation.ErrorRed
import presentation.FileCardGradiant
import presentation.GradientGreen
import presentation.HighBackground
import presentation.HighText
import presentation.MediumBackground
import presentation.MediumText
import presentation.NeonEffect
import presentation.NormalBackground
import presentation.NormalText
import presentation.PrimaryBlue
import presentation.PrimaryText
import presentation.PrimaryTextAlt2
import presentation.ShareButtonGradiant
import presentation.TertiaryGreen
import presentation.ViolateGradiant
import presentation.cornerStretchAnimation
import utils.KottieConstants
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    hideBottomBar: (Boolean) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    val forYouItems = viewModel.forYouUpdatedFlow.collectAsLazyPagingItems()
    val nearByItems = viewModel.nearByFlow.collectAsLazyPagingItems()
    val savedItems = viewModel.savedFlow.collectAsLazyPagingItems()

    HomeScreen(
        state = state,
        forYou = forYouItems,
        nearBy = nearByItems,
        saved = savedItems,
        onAction = viewModel::onAction,
        hideBottomBar = hideBottomBar,
    )

    ImageFullScreenDialog(
        state = dialogState,
        onAction = viewModel::onAction
    )

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    forYou: LazyPagingItems<Poster.Normal>,
    nearBy: LazyPagingItems<Poster.Normal>,
    saved: LazyPagingItems<Poster.Normal>,
    onAction: (HomeScreenAction) -> Unit,
    hideBottomBar: (Boolean) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    var emergencyAlertExpanded by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { HomeTab.entries.size })
    val scrollState = rememberScrollState()

    var topSectionHeight by remember { mutableStateOf(0) }
    var topSectionOffset by remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            // Collapse on downward scroll — section hides before notices start scrolling
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = (topSectionOffset + delta).coerceIn(
                    minimumValue = -topSectionHeight.toFloat(),
                    maximumValue = 0f
                )
                val consumed = newOffset - topSectionOffset
                topSectionOffset = newOffset
                return Offset(x = 0f, y = consumed)

            }
        }
    }
    val hideBottomBar by remember {
        derivedStateOf {
            topSectionOffset < 0f
        }
    }

    LaunchedEffect(hideBottomBar) {
        hideBottomBar(hideBottomBar)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                // Collapsible section: header + search bar + emergency card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            val height = (placeable.height + topSectionOffset)
                                .coerceAtLeast(0f).roundToInt()
                            layout(constraints.maxWidth, height) {
                                placeable.placeRelative(0, topSectionOffset.roundToInt())
                            }
                        }
                        .onGloballyPositioned { topSectionHeight = it.size.height }
                ) {
                    Column {
                        HeaderSection(state = state, onAction = onAction)

                        CustomSearchBar(
                            query = state.searchQuery,
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            onQueryChange = {
                                onAction(
                                    HomeScreenAction.OnSearchQueryChanged(
                                        it
                                    )
                                )
                            },
                            onSearch = { onAction(HomeScreenAction.OnSearchQueryChanged(it)) },
                            onHistoryClick = {
                                onAction(
                                    HomeScreenAction.OnSearchQueryChanged(
                                        it
                                    )
                                )
                            },
                            onSuggestionClick = {
                                onAction(HomeScreenAction.OnSearchQueryChanged(it))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        )

                        // Emergency carousel
                        if (state.emergencyNotice.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 8.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .background(
                                                brush = EmergencyGradient,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    )
                                    {
                                        Icon(
                                            painter = painterResource(Res.drawable.warning),
                                            contentDescription = "warning",
                                            modifier = Modifier.size(20.dp)
                                                .cornerStretchAnimation(),
                                            tint = EmergenceyAlertRedBG
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = "Emergency Alerts",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.SemiBold
                                                ),
                                                color = ErrorRed
                                            )
                                            Text(
                                                text = "${state.emergencyNotice.size} active alerts in your area",
                                                fontSize = 12.sp,
                                                color = EmergenceyAlertRedBG
                                            )
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "Show all",
                                            fontSize = 12.sp,
                                            color = PrimaryBlue,
                                            modifier = Modifier.clickable(
                                                onClick = {
                                                    emergencyAlertExpanded = !emergencyAlertExpanded
                                                    //onAction(HomeScreenAction.OnEmergencyAlertDismiss)
                                                }
                                            )
                                        )
                                        Icon(
                                            imageVector = Icons.Default.KeyboardArrowRight,
                                            contentDescription = "right_arrow",
                                            tint = PrimaryBlue,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable(
                                                    onClick = {
                                                        emergencyAlertExpanded =
                                                            !emergencyAlertExpanded
                                                        //onAction(HomeScreenAction.OnEmergencyAlertDismiss)
                                                    }
                                                )
                                        )
                                    }

                                    if (emergencyAlertExpanded) {
                                        state.emergencyNotice.forEach { it ->
                                            EmergencyNotice(
                                                poster = it,
                                                modifier = Modifier.padding(top = 8.dp),
                                                onNavigateToDetail = {}
                                            )
                                        }
                                    } else {
                                        EmergencyCarousel(
                                            notices = state.emergencyNotice,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .padding(bottom = 8.dp),
                                            onAction = onAction
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Tab row — always visible below the collapsible section
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HomeTab.entries.forEachIndexed { index, tab ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch { pagerState.animateScrollToPage(index) }
                            },
                            text = {
                                Text(
                                    text = tab.title,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        )
                    }
                }

                // HorizontalPager — handles swipe between tabs
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    val notices = when (HomeTab.entries[page]) {
                        HomeTab.FOR_YOU -> forYou
                        HomeTab.NEARBY -> nearBy
                        HomeTab.SAVED -> saved
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            end = 8.dp,
                            top = 8.dp,
                            bottom = 80.dp
                        )
                    ) {
                        items(
                            count = notices.itemCount,
                            key = notices.itemKey { it.id }
                        ) { index ->
                            NormalNotice(
                                poster = notices[index] ?: return@items,
                                onAction = onAction,
                                onNavigateToDetail = {},
                                modifier = Modifier.animateItem(
                                    placementSpec = spring(
                                        dampingRatio = 0.5f,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getIcon(poster: Poster.Emergency): String {
    return when (poster.topic) {
        Topic.FIRE -> "files/fire.json"
        Topic.FLOOD -> TODO() // painterResource(Res.drawable.flood)
        Topic.LOAD_SHEDDING -> TODO() // painterResource(Res.drawable.load_shedding)
        Topic.ROAD_CONSTRUCTION -> TODO() // painterResource(Res.drawable.road_construction)
        Topic.EARTHQUAKE -> TODO()
        Topic.GAS_LEAK -> "files/gas_leak.json"
        Topic.ACCIDENT -> "files/accident.json"
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

enum class HomeTab(val title: String) {
    FOR_YOU("For You"),
    NEARBY("Nearby"),
    SAVED("saved")
}

/*@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
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
}*/

/*@Preview
@Composable
fun NormalPosterPreview() {
    NormalNotice(
        modifier = Modifier,
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
        onImageClick = { },
        sharedTransitionScope = ,
        animatedVisibilityScope =
    )
}*/

@Preview
@Composable
fun EmergencyCarouselPreview() {
    EmergencyCarousel(
        notices = listOf(
            Poster.Emergency(
                0,
                "Flash Flood Warning, Please Stay Away From There",
                "Heavy rainfall causing flood in low-laying areas.",
                "5/11/2025",
                "2km away",
                "5 min ago",
                "",
                "Dhanmondi Area, Near Dhaka University",
                Type.HIGH,
                Topic.FIRE
            ),
            Poster.Emergency(
                1,
                "Gas Leak Alert in Residential Area",
                "A major gas leakage has been reported.",
                "6/11/2025",
                "800m away",
                "10 min ago",
                "",
                "Mirpur Section 10",
                Type.MEDIUM,
                Topic.GAS_LEAK
            )
        ),
        onAction = {}
    )
}


