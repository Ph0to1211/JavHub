package com.jadesoft.javhub.presentation.library

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.jadesoft.javhub.ui.library.LibraryEvent

@Composable
fun LibraryDeleteDialog(
    onDismiss: (LibraryEvent.OnToggleShowDialog) -> Unit,
    onConfirm: (LibraryEvent.DeleteItems) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(LibraryEvent.OnToggleShowDialog)
        },
        title = { Text("是否删除") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(LibraryEvent.DeleteItems)
                    onDismiss(LibraryEvent.OnToggleShowDialog)
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss(LibraryEvent.OnToggleShowDialog)
            }) {
                Text("取消")
            }
        }
    )
}