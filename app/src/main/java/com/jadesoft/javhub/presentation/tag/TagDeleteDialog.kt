package com.jadesoft.javhub.presentation.tag

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jadesoft.javhub.ui.tag.TagEvent

@Composable
fun TagDeleteDialog(
    index: Int,
    onDismiss: (TagEvent.DismissDialog) -> Unit,
    onConfirm: (index: Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(TagEvent.DismissDialog)
        },
        title = { Text("是否删除") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(index)
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss(TagEvent.DismissDialog)
            }) {
                Text("取消")
            }
        }
    )
}