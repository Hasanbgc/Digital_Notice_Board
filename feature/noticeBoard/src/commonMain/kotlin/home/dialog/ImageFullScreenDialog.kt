package home.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import home.HomeScreenAction
import home.Poster
import kotlinx.coroutines.launch
import presentation.NeutralGray500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageFullScreenDialog(
    state: DialogState?,
    onAction: (HomeScreenAction) -> Unit
) {


    state?.let {
        Dialog(
            onDismissRequest = {
                onAction(HomeScreenAction.OnDismissDialog)
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            DialogWithCarousal(it.imageUrl + it.imageUrl + it.imageUrl, it.imagePosition){
                onAction(HomeScreenAction.OnDismissDialog)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWithCarousal(
    imageList: List<String>,
    initialPosition: Int = 0,
    modifier: Modifier = Modifier,
    onAction: () -> Unit = {}
) {
    val pagerState = rememberPagerState(
        initialPage = initialPosition,
        pageCount = { imageList.size }
    )
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(0.75f)),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .weight(1f),
                key = { page -> "main_$page" }
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.33f) // 4f/3f
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = imageList[page],
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }

            HorizontalUncontainedCarousel(
                state = rememberCarouselState { imageList.count() },
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 16.dp),
                itemWidth = 100.dp,
                itemSpacing = 8.dp,
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) { i ->
                val item = imageList[i]
                AsyncImage(
                    model = item,
                    contentDescription = "image",
                    modifier = Modifier
                        .height(100.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge)
                        .clickable {
                            scope.launch {
                                pagerState.animateScrollToPage(i)
                            }
                        },
                    contentScale = ContentScale.Crop,
                )
            }
        }

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "right_arrow",
            tint = Color.White,
            modifier = Modifier
                .size(38.dp)
                .align(Alignment.TopEnd)
                .padding(end = 10.dp, top = 10.dp)
                .background(color = NeutralGray500, shape = RoundedCornerShape(30.dp))
                .clickable { onAction()}
        )
    }
}