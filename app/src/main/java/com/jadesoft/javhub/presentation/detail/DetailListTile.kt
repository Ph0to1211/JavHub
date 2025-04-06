package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailListTile(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 5.dp)
    )
}