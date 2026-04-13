package home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    expanded: Boolean,
    placeholder: String = "Search...",
    history: List<String> = emptyList(),
    suggestions: List<String> = emptyList(),
    searchResults: List<String> = emptyList(),
    onQueryChange: (String) -> Unit,
    onExpandedChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    onHistoryClick: (String) -> Unit,
    onSuggestionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val filteredSuggestion by remember(query, suggestions) {
        derivedStateOf {
            if (query.isNotEmpty()) {
                suggestions.filter { it.contains(query, ignoreCase = true) }
            } else {
                suggestions
            }
        }
    }

    val showHistory by remember(query, history, expanded) {
        derivedStateOf {
            query.isEmpty() && history.isNotEmpty() && expanded
        }
    }
    val showSuggestion by remember(query, filteredSuggestion, expanded) {
        derivedStateOf {
            query.isNotEmpty() && filteredSuggestion.isNotEmpty() && expanded
        }
    }
    val showSearchResult by remember(query, expanded) {
        derivedStateOf {
            query.isNotEmpty() && expanded
        }
    }



    SearchBar(
        inputField = @Composable {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { query ->
                    onQueryChange(query)
                },
                onSearch = {
                    onSearch(query)
                    onExpandedChange(false)
                },
                expanded = expanded,
                onExpandedChange = { exp -> onExpandedChange(exp) },
                placeholder = { Text(placeholder) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    Row {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                        if (expanded) {
                            IconButton(onClick = { onExpandedChange(false) }) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                }
            )
        },
        expanded = expanded,
        onExpandedChange = { onExpandedChange(it) },
        shape = RoundedCornerShape(28.dp),
        modifier = modifier,
        tonalElevation = 6.dp,
        shadowElevation = 2.dp,
        windowInsets = WindowInsets(0.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.White,
            )
    ) {
        when {
            showHistory -> {
                SearchHistoryContent(
                    history = history,
                    onHistoryClick = { query ->
                        onHistoryClick(query)
                        onSearch(query)
                        onExpandedChange(false)
                    },
                    onHistoryRemoved = {},
                )
            }

            showSuggestion -> {
                SuggestionsContent(
                    suggestions = filteredSuggestion,
                    onSuggestionClick = {
                        onSuggestionClick(it)
                    }
                )
            }

            showSearchResult -> {
                SearchResultContent(
                    searchResult = searchResults,
                    onSearchResultClick = {
                        onSearch(it)
                    }
                )
            }
        }

    }
}

@Composable
private fun SearchHistoryContent(
    history: List<String>,
    onHistoryClick: (String) -> Unit,
    onHistoryRemoved: (String) -> Unit,
) {
    if (history.isEmpty()) {
        EmptyStateContent(
            message = "No Search History",
            icon = Icons.Default.SearchOff,
        )
    } else {
        LazyColumn {
            items(history) { historyItem ->
                ListItem(
                    headlineContent = { Text(text = historyItem) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null
                        )
                    },
                    trailingContent = {
                        IconButton(onClick = { onHistoryRemoved(historyItem) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    },
                    modifier = Modifier.clickable { onHistoryClick(historyItem) }
                )
            }
        }
    }
}

@Composable
private fun SuggestionsContent(
    suggestions: List<String>,
    onSuggestionClick: (String) -> Unit,
) {
    if (suggestions.isEmpty()) {
        EmptyStateContent(
            message = "No Suggestions",
            icon = Icons.Default.SearchOff,
        )
    } else {
        LazyColumn {
            items(suggestions) { suggestionItem ->
                ListItem(
                    headlineContent = { Text(suggestionItem) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable { onSuggestionClick(suggestionItem) }
                )
            }
        }
    }
}

@Composable
private fun SearchResultContent(
    searchResult: List<String>,
    onSearchResultClick: (String) -> Unit,
) {
    if (searchResult.isEmpty()) {
        EmptyStateContent(
            message = "No Search Result",
            icon = Icons.Default.SearchOff,
        )
    } else {
        LazyColumn {
            items(searchResult) { searchResultItem ->
                ListItem(
                    headlineContent = { Text(searchResultItem) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.clickable { onSearchResultClick(searchResultItem) }
                )
            }
        }
    }
}


@Composable
private fun EmptyStateContent(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    message: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
