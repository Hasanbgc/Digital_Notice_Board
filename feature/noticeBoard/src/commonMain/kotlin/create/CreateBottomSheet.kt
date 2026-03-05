package create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import digita_notice_board.feature.noticeboard.generated.resources.Res
import digita_notice_board.feature.noticeboard.generated.resources.send
import org.jetbrains.compose.resources.painterResource
import presentation.GradientGreen
import presentation.GradientInactiveBg
import presentation.PrimaryTextAlt2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoticeBottomSheet(
    sheetState: SheetState,
    state: CreateNoticeScreenState?,
    onAction: (CreateNoticeScreenAction) -> Unit,
) {

    state?.let {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            sheetState = sheetState,
            onDismissRequest = { onAction(CreateNoticeScreenAction.OnDismiss) },
            dragHandle = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    // Center drag handle
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier.align(Alignment.Center)
                    )

                    // Dismiss button (end)
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        onClick = { onAction(CreateNoticeScreenAction.OnDismiss) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Dismiss"
                        )
                    }
                }
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    Text(
                        text = "Create Notice",
                        modifier = Modifier
                            .align(Alignment.Start),
                        style = TextStyle(
                            brush = GradientGreen,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )

                    )
                    Text(
                        text = "Share important information with your community",
                        modifier = Modifier
                            .align(Alignment.Start),
                        color = PrimaryTextAlt2,
                        fontSize = 12.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
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
                    onClick = { onAction(CreateNoticeScreenAction.PublishNoticeClicked) },
                    shape = RoundedCornerShape(16.dp),
                    enabled = state.currentStep == NoticeCreationStep.ADD_NOTICE_BODY,
                    contentPadding = PaddingValues(),
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
                                contentDescription = "Dismiss",
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
}