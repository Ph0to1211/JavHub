package com.jadesoft.javhub.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.db.dto.SearchHistoryEntity
import com.jadesoft.javhub.ui.search.SearchEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchHistoryContent(
    searchHistories: List<SearchHistoryEntity>,
    deleteSingleSearchHistory: (SearchEvent.DeleteSingleSearchHistory) -> Unit,
    deleteSearchHistory: (SearchEvent.DeleteSearchHistory) -> Unit,
    onHistoryItemClicked: (SearchEvent.OnHistoryItemClicked) -> Unit
) {
    val scrollState = rememberScrollState()

    if (searchHistories.isNotEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState).padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "搜索历史",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Button(
                    onClick = { deleteSearchHistory(SearchEvent.DeleteSearchHistory) }
                ) {
                    Text("全部删除")
                }
            }
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                searchHistories.forEach{ history ->
                    key(history.id) {
                        InputChip(
                            selected = false,
                            onClick = { onHistoryItemClicked(
                                    SearchEvent.OnHistoryItemClicked(history.query)
                                ) },
                            label = {
                                Text(history.query, style = MaterialTheme.typography.bodyLarge)
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "",
                                    modifier = Modifier.clickable {
                                        deleteSingleSearchHistory(SearchEvent.DeleteSingleSearchHistory(history.query))
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("它需要做些什么...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}