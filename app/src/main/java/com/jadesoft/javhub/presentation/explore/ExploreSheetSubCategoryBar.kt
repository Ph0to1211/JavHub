package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.ui.explore.ExploreEvent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreSheetSubCategoryBar(
    isLoading: Boolean,
    genres: Map<String, List<Genre>>,
    filterOptions: List<Genre>,
    editFilterOptions: (ExploreEvent.EditFilterOption) -> Unit
) {
    if (isLoading) {
        CircularLoading()
    } else {
        LazyColumn{
            genres.forEach { (mainGenre, subGenres) ->
                item(key = mainGenre) {
                    Text(
                        text = mainGenre,
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                item {
                    FlowRow(
                        modifier = Modifier.padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp), // 水平间距
                    ) {
                        subGenres.forEach { genre ->
                            key(genre.subGenre) {
                                FilterChip(
                                    onClick = {
                                        editFilterOptions(
                                            ExploreEvent.EditFilterOption(genre = genre)
                                        )
                                    },
                                    label = { Text(genre.subGenre) },
                                    selected = filterOptions.contains(genre),
                                    leadingIcon = if (filterOptions.contains(genre)) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Done,
                                                contentDescription = "Done icon",
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    } else null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}