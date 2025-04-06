package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.explore.ExploreEvent

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExploreSheetDisplayBar(
    itemStyle: Int,
    itemNum: Int,
    toggleItemStyle: (ExploreEvent.ToggleItemStyle) -> Unit,
    toggleItemNum: (ExploreEvent.ToggleItemNum) -> Unit
) {
    val options = listOf("紧凑卡片", "松散卡片", "封面卡片", "列表")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "显示模式",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEachIndexed { index, option ->
                FilterChip(
                    onClick = { toggleItemStyle(
                        ExploreEvent.ToggleItemStyle(option = index)
                    ) },
                    label = { Text(option) },
                    selected = itemStyle == index,
                    leadingIcon = if (itemStyle == index) {
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

        if (itemStyle != 3) {
            Text(
                text = "显示条数",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            )
            ExploreSheetSlider(itemNum, toggleItemNum)
        }
    }
}