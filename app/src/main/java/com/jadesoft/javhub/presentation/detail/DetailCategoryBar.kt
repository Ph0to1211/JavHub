package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Link
import com.jadesoft.javhub.ui.movie.ListType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailCategoryBar(
    categories: List<Link>,
    censored: Boolean,
    onClick: (String, String, Boolean, ListType) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp) // 设置水平间距
    ) {
        categories.forEach { item ->
            SuggestionChip(
                onClick = { onClick(item.code, item.name, censored, ListType.Genre) },
                label = {
                    Text(item.name, style = MaterialTheme.typography.bodySmall)
                },
            )
        }
    }
}