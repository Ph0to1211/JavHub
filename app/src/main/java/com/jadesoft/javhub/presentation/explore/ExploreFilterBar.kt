package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.explore.ExploreEvent
import com.jadesoft.javhub.ui.explore.ExploreViewModel
import com.jadesoft.javhub.ui.home.ShareViewModel

@Composable
fun ExploreFilterBar(
    showUncensored: Boolean,
    isFiltered: Boolean,
    filterName: String,
    exploreType: Boolean,
    toggleFilter: (ExploreEvent.ToggleFilter) -> Unit,
    toggleGenre: (ExploreEvent.ToggleGenre) -> Unit,
    resetFilterOption: (ExploreEvent.ResetFilter) -> Unit
) {

    val options = listOf("🐎 有码", "🚶‍♀️ 无码")

    Column {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
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
            Spacer(Modifier.width(10.dp))
            if (exploreType) InputChip(
                onClick = { toggleFilter(
                    ExploreEvent.ToggleFilter
                ) },
                label = { Text(
                    text = if (filterName.isNotEmpty()) "筛选: ${filterName}" else "筛选",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = if (isFiltered) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium,
                    modifier = if (isFiltered) Modifier.padding(top = 3.dp, bottom = 3.dp) else Modifier
                ) },
                selected = isFiltered,
                leadingIcon = {
                    Icon(
                        Icons.Filled.FilterList,
                        contentDescription = "Filter",
//                        Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                trailingIcon = {
                    if (isFiltered) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "取消筛选",
                            modifier = Modifier.clickable { resetFilterOption(
                                ExploreEvent.ResetFilter
                            ) }
                        )
                    }
                },
            )
        }
        HorizontalDivider()
    }
}