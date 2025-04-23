package com.jadesoft.javhub.presentation.tag

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jadesoft.javhub.ui.tag.TagEvent

@Composable
fun TagEditDialog(
    initialName: String,
    onDismiss: (TagEvent.DismissDialog) -> Unit,
    onConfirm: (String) -> Unit
) {
    var input by remember { mutableStateOf(initialName) }

    AlertDialog(
        onDismissRequest = {
            onDismiss(TagEvent.DismissDialog)
        },
        title = { Text("编辑标签") },
        text = {
            OutlinedTextField(
                label = { Text("名称") },
                supportingText = { Text("*必填") },
                value = input,
                onValueChange = { input = it }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(input)
                },
                enabled = input.isNotBlank() && input != initialName
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