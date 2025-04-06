package com.jadesoft.javhub.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HistoryDateItem(date: String) {
    Text(
        text = date,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = MaterialTheme.typography.titleMedium
    )
}