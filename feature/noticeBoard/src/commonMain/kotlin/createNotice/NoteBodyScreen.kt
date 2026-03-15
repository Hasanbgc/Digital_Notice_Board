package createNotice

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import presentation.BGGreen
import presentation.BGGreenIcon
import presentation.BGRed
import presentation.BGRedIcon
import presentation.NeutralGray500
import presentation.PrimaryText
import presentation.PrimaryTextAlt2
import presentation.SecondaryGreen
import presentation.TertiaryGreen
import presentation.VerifyTitleColor

@Composable
fun NoteBodyScreen(
    state: CreateNoticeScreenState,
    onAction: (CreateNoticeScreenAction) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("")}
    val maxLength = 100
    val titleCount = title.length
    val detailsCount = details.length

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState(0)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Selected Category",
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = SecondaryGreen,
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, getColor(state.selectedCategory?.id).first),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = getColor(state.selectedCategory?.id).third)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .background(
                            getColor(state.selectedCategory?.id).second,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = state.selectedCategory?.icon!!,
                        contentDescription = state.selectedCategory.title,
                        modifier = Modifier.size(20.dp),
                        tint = getColor(state.selectedCategory.id).first
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f).padding(4.dp)) {
                    Text(
                        text = state.selectedCategory?.title!!,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = getColor(state.selectedCategory.id).first,
                        )
                    )
                    Text(
                        text = state.selectedCategory.description,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Normal,
                            color = PrimaryTextAlt2
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .clickable {
                            onAction(CreateNoticeScreenAction.OnCategoryChangeClicked)
                        },
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "Change",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = getColor(state.selectedCategory?.id).first,
                        )
                    )
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Notice Title",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText,
                    )
                )
                Text(
                    text = "$titleCount/100",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Normal,
                        color = NeutralGray500
                    )
                )
            }
            TextField(
                value = title,
                onValueChange = { newValue ->
                    if (newValue.length <= maxLength) {
                        title = newValue
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Enter Notice Title")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = VerifyTitleColor,
                    focusedPlaceholderColor = NeutralGray500,
                    unfocusedPlaceholderColor = NeutralGray500
                )

            )
        }
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Details",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryText,
                    )
                )
            }
            TextField(
                value = details,
                onValueChange = { newValue ->
                    details = newValue
                },
                modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp, max = 200.dp),
                shape = RoundedCornerShape(8.dp),
                placeholder = {
                    Text(text = "Write details here...")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = VerifyTitleColor,
                    focusedPlaceholderColor = NeutralGray500,
                    unfocusedPlaceholderColor = NeutralGray500
                )

            )
        }



    }
}

private fun getColor(id: Int?): Triple<Color, Color, Color> {
    return when (id) {
        1 -> Triple(Color.Red, BGRedIcon, BGRed)
        2 -> Triple(TertiaryGreen, BGGreenIcon, BGGreen)
        else -> {
            Triple(TertiaryGreen, BGGreenIcon, BGGreen)
        }
    }
}
