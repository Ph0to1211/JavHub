package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.ui.explore.ExploreEvent

@Composable
fun ExploreSheetCategoryBar(
    isLoading: Boolean,
    genres: Map<String, List<Genre>>,
    onlyShowMag: Boolean,
    showUncensored: Boolean,
    filterOptions: List<Genre>,
    resetFilterOption: (ExploreEvent.ResetFilter) -> Unit,
    submitFilterOption: (ExploreEvent.SubmitFilter) -> Unit,
    toggleGenre: (ExploreEvent.ToggleGenre) -> Unit,
    toggleMag: (ExploreEvent.ToggleMag) -> Unit,
    editFilterOptions: (ExploreEvent.EditFilterOption) -> Unit
) {
    val options = listOf("🐎 有码", "🚶‍♀️ 无码")

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = {
                    resetFilterOption(
                        ExploreEvent.ResetFilter
                    )
                }
            ) {
                Text("重置")
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    submitFilterOption(
                        ExploreEvent.SubmitFilter
                    )
                }
            ) {
                Text("筛选")
            }
        }
        Text(
            text = "主要类别",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.width(200.dp)
            ) {
                options.forEachIndexed{ index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = options.size
                        ),
                        onClick = {
                            toggleGenre(
                                ExploreEvent.ToggleGenre
                            )
                        },
                        selected = (index == 0) != showUncensored,
                        label = { Text(label) }
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = onlyShowMag,
                    onCheckedChange = {
                        toggleMag(
                            ExploreEvent.ToggleMag
                        )
                    }
                )
                Text("已有磁力", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Text(
            text = "次要类别",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )
        ExploreSheetSubCategoryBar(isLoading, genres, filterOptions, editFilterOptions)
    }
}