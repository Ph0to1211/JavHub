package com.jadesoft.javhub.presentation.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.search.SearchEvent
import com.jadesoft.javhub.ui.search.SearchViewModel

@Composable
fun SearchFilterBar(
    selectedIndex: Int,
    modifierType: (SearchEvent.ModifierType) -> Unit
) {
    val categories = listOf("有码影片", "无码影片", "女优", "导演", "制作商", "发行商", "系列")
    val scrollState = rememberScrollState()

    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState).padding(start = 10.dp, end = 10.dp)
        ) {
            categories.forEachIndexed { index, item ->
                FilterChip(
                    onClick = {
                        modifierType(SearchEvent.ModifierType(index))
                    },
                    label = { Text(item) },
                    selected = index == selectedIndex,
                    enabled = true
                )
            }
        }
        HorizontalDivider()
    }
}