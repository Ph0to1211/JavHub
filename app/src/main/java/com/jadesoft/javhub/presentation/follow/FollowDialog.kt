package com.jadesoft.javhub.presentation.follow

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.jadesoft.javhub.ui.follow.FollowEvent

@Composable
fun FollowDialog(
    onDismiss: (FollowEvent.ToggleShowDialog) -> Unit,
    onConfirm: (FollowEvent.OnRemoveFollow) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss(FollowEvent.ToggleShowDialog(null))
        },
        title = { Text("是否取消关注") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(FollowEvent.OnRemoveFollow)
                    onDismiss(FollowEvent.ToggleShowDialog(null))
                }
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss(FollowEvent.ToggleShowDialog(null))
            }) {
                Text("取消")
            }
        }
    )
}