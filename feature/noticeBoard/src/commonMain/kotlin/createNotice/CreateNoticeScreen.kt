package createNotice

import rememberCurrentLocationProvider
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.send
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import presentation.GradientGreen
import presentation.GradientInactiveBg
import presentation.PrimaryTextAlt2


@Composable
fun CreateNoticeRoot(
    viewModel: CreateNoticeViewModel = koinViewModel(),
    onNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CreateNoticeScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigate = onNavigate
    )
}

@Composable
fun CreateNoticeScreen(
    state: CreateNoticeScreenState,
    onAction: (CreateNoticeScreenAction) -> Unit,
    onNavigate: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val getCurrentLocation = rememberCurrentLocationProvider()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (state.currentStep == NoticeCreationStep.QUICK_PICK_CATEGORY) {
                            onNavigate()
                        } else {
                            onAction(CreateNoticeScreenAction.OnCategoryChangeClicked)
                        }
                    },
                    modifier = Modifier.border(BorderStroke(0.5.dp,color = Color.LightGray) , shape = RoundedCornerShape(32.dp))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = "Create Notice",
                        style = TextStyle(
                            brush = GradientGreen,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "Share important information with your community",
                        color = PrimaryTextAlt2,
                        fontSize = 12.sp
                    )
                }
            }

            Box(modifier = Modifier.weight(1f).padding(top = 16.dp)) {
                when (state.currentStep) {
                    NoticeCreationStep.QUICK_PICK_CATEGORY -> {
                        QuickPickCategoryScreen(state = state, onAction = onAction)
                    }

                    NoticeCreationStep.PICK_CATEGORY -> {}
                    NoticeCreationStep.ADD_NOTICE_BODY -> {
                        NoteBodyScreen(state = state, onAction = onAction)
                    }

                    else -> {}
                }
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        val hasLocation = state.attachments.any { it.type == AttachmentType.LOCATION }
                        if (!hasLocation) {
                            getCurrentLocation()?.let { (lat, lon) ->
                                onAction(
                                    CreateNoticeScreenAction.OnAttachmentsAdded(
                                        listOf(
                                            Attachment(
                                                id = generateAttachmentId(),
                                                name = "Current Location",
                                                type = AttachmentType.LOCATION,
                                                uri = "$lat,$lon"
                                            )
                                        )
                                    )
                                )
                            }
                        }
                        onAction(CreateNoticeScreenAction.PublishNoticeClicked)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                enabled = state.currentStep == NoticeCreationStep.ADD_NOTICE_BODY,
                contentPadding = PaddingValues(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = if (state.currentStep == NoticeCreationStep.ADD_NOTICE_BODY) GradientGreen else GradientInactiveBg,
                            shape = RoundedCornerShape(16.dp)
                        ).padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            painter = painterResource(Res.drawable.send),
                            contentDescription = "Publish",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Publish Now",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }

    }
}


@Preview
@Composable
fun CreateNoticeScreenQuickPickPreview() {
    MaterialTheme {
        CreateNoticeScreen(
            state = CreateNoticeScreenState(
                currentStep = NoticeCreationStep.QUICK_PICK_CATEGORY,
                quickPickCategory = categories
            ),
            onAction = {},
            onNavigate = {}
        )
    }
}

@Preview
@Composable
fun CreateNoticeScreenAddBodyPreview() {
    MaterialTheme {
        CreateNoticeScreen(
            state = CreateNoticeScreenState(
                currentStep = NoticeCreationStep.ADD_NOTICE_BODY,
                selectedCategory = categories.firstOrNull()
            ),
            onAction = {},
            onNavigate = {}
        )
    }
}
