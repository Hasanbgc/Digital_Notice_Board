package createNotice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import home.component.CustomSearchBar
import presentation.BorderGray
import presentation.PrimaryTextAlt2

@Composable
fun QuickPickCategoryScreen(
    state: CreateNoticeScreenState,
    onAction: (CreateNoticeScreenAction) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CustomSearchBar(
            state.searchQuery,
            expanded = false,
            onExpandedChange = { expanded = it},
            onQueryChange = { onAction(CreateNoticeScreenAction.OnSearchQueryChanged(it)) },
            onSearch = { onAction(CreateNoticeScreenAction.OnSearchQueryChanged(it))},
            onHistoryClick = {onAction(CreateNoticeScreenAction.OnSearchQueryChanged(it))},
            onSuggestionClick = {onAction(CreateNoticeScreenAction.OnSearchQueryChanged(it))},
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Notice Category",
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            )
        )

        Text(
            text = "Choose what best describes your notice",
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = FontWeight.Normal,
                color = PrimaryTextAlt2,
                )
        )
        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier,
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(
                items = state.quickPickCategory,
                key = {category -> category.id}
            ){ category ->
                QuickPickCategoryItem(
                    category = category,
                    onAction = onAction
                )
            }

        }
    }
}

@Composable
fun QuickPickCategoryItem(
    category: Category,
    onAction: (CreateNoticeScreenAction) -> Unit
) {
    Card(
        modifier = Modifier.clickable {
             onAction(CreateNoticeScreenAction.PickedCategory(category))
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
    ){
        Column (
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ){
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(0.5.dp, color = BorderGray)
            ){
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.title,
                    modifier = Modifier.size(34.dp).padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = category.title,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.description,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = PrimaryTextAlt2
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}