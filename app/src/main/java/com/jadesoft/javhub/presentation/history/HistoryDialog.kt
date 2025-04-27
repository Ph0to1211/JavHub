package com.jadesoft.javhub.presentation.history

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.jadesoft.javhub.ui.history.HistoryEvent

@Composable
fun HistoryDialog(
    onDismiss: (HistoryEvent.ToggleShowDialog) -> Unit,
    onConfirm: (HistoryEvent.DeleteAllHistory) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(HistoryEvent.ToggleShowDialog)
        },
        title = { Text("是否删除") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(HistoryEvent.DeleteAllHistory)
                    onDismiss(HistoryEvent.ToggleShowDialog)
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss(HistoryEvent.ToggleShowDialog)
            }) {
                Text("取消")
            }
        }
    )
}