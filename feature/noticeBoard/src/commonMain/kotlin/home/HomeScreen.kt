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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    hideBottomBar: (Boolean) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
    val forYouItems = viewModel.forYouFlow.collectAsLazyPagingItems()
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

    var topSectionHeight by remember { mutableStateOf(0) }
    var topSectionOffset by remember { mutableStateOf(0f) }

    val hideBottomBar by remember {
        derivedStateOf {
            topSectionOffset < 0f
        }
    }
    LaunchedEffect(hideBottomBar){
        hideBottomBar(hideBottomBar)
    }


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

    Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

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
                            onQueryChange = { onAction(HomeScreenAction.OnSearchQueryChanged(it)) },
                            onSearch = { onAction(HomeScreenAction.OnSearchQueryChanged(it)) },
                            onHistoryClick = { onAction(HomeScreenAction.OnSearchQueryChanged(it)) },
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

                                    /*AnimatedVisibility(
                                    visible = emergencyAlertExpanded,
                                    ){

                                    }*/
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


@Composable
fun EmergencyCarousel(
    notices: List<Poster.Emergency>,
    modifier: Modifier = Modifier,
    onAction: (HomeScreenAction) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5_000)
            if (!pagerState.isScrollInProgress) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            EmergencyCarouselItem(
                notice = notices[page % notices.size],
                modifier = Modifier.padding(top = 8.dp)
            )
            /*EmergencyNotice(
                poster = notices[page % notices.size],
                onNavigateToDetail = {}
            )*/
        }

        // Page indicator dots
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(notices.size) { index ->
                val isSelected = (pagerState.currentPage % notices.size) == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (isSelected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) ErrorRed else BorderGray)
                )
            }
        }
    }

}

@Composable
fun EmergencyCarouselItem(
    notice: Poster.Emergency,
    modifier: Modifier = Modifier
) {
    var animation by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        animation = Res.readBytes(getIcon(notice)).decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = KottieConstants.IterateForever
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .NeonEffect()
            .background(brush = EmergencyGradient, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        KottieAnimation(
            composition = composition,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(EmergencyIconBG)
                .padding(5.dp),
            progress = { animationState.progress }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = notice.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(11.dp),
                    tint = PrimaryTextAlt2
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = notice.location,
                    fontSize = 11.sp,
                    color = PrimaryTextAlt2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = null,
                    modifier = Modifier.size(11.dp),
                    tint = PrimaryTextAlt2
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = notice.time,
                    fontSize = 11.sp,
                    color = PrimaryTextAlt2
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (notice.type) {
                    Type.HIGH -> HighBackground
                    Type.MEDIUM -> MediumBackground
                    Type.NORMAL -> NormalBackground
                    else -> EmergencyIconBG
                }
            )
        ) {
            Text(
                text = notice.type.name,
                fontSize = 11.sp,
                color = when (notice.type) {
                    Type.HIGH -> HighText
                    Type.MEDIUM -> MediumText
                    Type.NORMAL -> NormalText
                    else -> ErrorRed
                },
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
fun EmergencyNotice(
    poster: Poster.Emergency,
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit
) {
    var animation by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        animation = Res.readBytes(getIcon(poster)).decodeToString()
    }
    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.JsonString(animation)
    )
    val animationState by animateKottieCompositionAsState(
        composition = composition,
        iterations = KottieConstants.IterateForever
    )

    Row(
        modifier = modifier then Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 500))
            .NeonEffect()
            .background(brush = EmergencyGradient, shape = RoundedCornerShape(8.dp))
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
                    colors = CardDefaults.cardColors(
                        containerColor = when (poster.type) {
                            Type.HIGH -> HighBackground
                            Type.MEDIUM -> MediumBackground
                            Type.NORMAL -> NormalBackground
                            else -> {
                                EmergencyIconBG
                            }
                        }
                    )
                )
                {
                    Text(
                        text = poster.type.name,
                        fontSize = 12.sp,
                        color = when (poster.type) {
                            Type.HIGH -> HighText
                            Type.MEDIUM -> MediumText
                            Type.NORMAL -> NormalText
                            else -> {
                                ErrorRed
                            }
                        },
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
    modifier: Modifier = Modifier,
    poster: Poster.Normal,
    onAction: (HomeScreenAction) -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showSeeMore by remember { mutableStateOf(false) }
    var toggleLiked by remember { mutableStateOf(false) }


    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(durationMillis = 300))
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
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
                                text = poster.profile.name + poster.id,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.background(
                                brush = GradientGreen,
                                shape = RoundedCornerShape(8.dp)
                            ).padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = "notification",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Education",
                                fontSize = 12.sp,
                                color = Color.White,
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "location",
                            modifier = Modifier.size(20.dp)
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
                                    modifier = Modifier.fillMaxSize().clickable {
                                        onAction(
                                            HomeScreenAction.OnImageClicked(
                                                poster.imageUrlList,
                                                0
                                            )
                                        )
                                    },
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
                                        modifier = Modifier.fillMaxSize().clickable {
                                            onAction(
                                                HomeScreenAction.OnImageClicked(
                                                    poster.imageUrlList,
                                                    0
                                                )
                                            )
                                        },
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
                                            modifier = Modifier.fillMaxSize().clickable {
                                                onAction(
                                                    HomeScreenAction.OnImageClicked(
                                                        poster.imageUrlList,
                                                        1
                                                    )
                                                )
                                            },
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
                            onAction(HomeScreenAction.OnLikeClicked(poster.id, toggleLiked))
                        }
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
                        when (poster.liked) {
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
                                    modifier = Modifier.size(14.dp).graphicsLayer {
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
                    painter = painterResource(if (poster.isSaved) Res.drawable.flaged else Res.drawable.flag),
                    contentDescription = "right_arrow",
                    tint = if (poster.isSaved) TertiaryGreen else Color.Black,
                    modifier = Modifier.size(20.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onAction(HomeScreenAction.OnSavedClicked(poster.id))
                        },
                )
            }
        }
    }
}

@Composable
fun AttachmentCard(
    attachment: String
) {
    Card(
        border = BorderStroke(0.5.dp, BorderGray),
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

@Composable
fun HeaderSection(
    state: HomeScreenState,
    onAction: (HomeScreenAction) -> Unit
) {
    val searchToggle by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Column(
            modifier = Modifier.wrapContentHeight().padding(all = 8.dp),
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
        ) {
            /*RoundGradientButton(
                modifier = Modifier.wrapContentSize(),
                text = "Search",
                icon = Icons.Default.Search,
                iconTint = Color.White,
                gradientColors = GradientGreen,
                shape = RoundedCornerShape(30.dp),
                onClick = { searchToggle != searchToggle }
            )*/

            Spacer(Modifier.width(4.dp))
            AnimatedVisibility(state.emergencyAlertClosed) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = ErrorRed,
                            contentColor = Color.White
                        ) {
                            Text(state.emergencyNotice.size.toString())
                        }
                    }
                ) {
                    RoundGradientButton(
                        modifier = Modifier.wrapContentSize(),
                        text = "notification",
                        icon = Icons.Outlined.Notifications,
                        iconTint = Color.White,
                        gradientColors = SolidColor(EmergenceyAlertRedBG),
                        shape = RoundedCornerShape(30.dp),
                        onClick = { onAction(HomeScreenAction.OnNotificationClicked) }
                    )
                }

            }
        }
    }
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

@Preview
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
        onNavigateToDetail = { }
    )
}

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


