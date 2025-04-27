package com.jadesoft.javhub.presentation.history

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.history.HistoryEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopBar(
    hasHistories: Boolean,
    isScrolled: Boolean,
    onToggleShowDialog: (HistoryEvent.ToggleShowDialog) -> Unit
) {
    TopAppBar(
        title = { Text("历史") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = with(MaterialTheme.colorScheme) {
                surfaceColorAtElevation(if (isScrolled) 3.dp else 0.dp)
            }
        ),
        actions = {
            Row {
                if (hasHistories)
                    IconButton(
                        onClick = { onToggleShowDialog(HistoryEvent.ToggleShowDialog) }
                    ) {
                        Icon(Icons.Outlined.DeleteSweep, "clear all")
                    }
            }
        }
    )
}