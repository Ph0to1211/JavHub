package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.detail.DetailEvent

@Composable
fun DetailDialog(
    cover: String,
    showDialog: Boolean,
    onDismiss: (DetailEvent.ToggleShowDialog) -> Unit,
    onConfirm: (DetailEvent.AddToLibrary) -> Unit,
    editTags: (DetailEvent.EditTags) -> Unit,
    tags: List<String>,
    selectedTags: List<String>
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss(DetailEvent.ToggleShowDialog)
            },
            title = { Text("添加到库中", style = MaterialTheme.typography.titleMedium) },
            text = {
                Column {
                    Text("选择标签", style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(12.dp))

                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        tags.forEach { tag ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            ) {
                                Checkbox(
                                    checked = selectedTags.contains(tag),
                                    onCheckedChange = { editTags(DetailEvent.EditTags(tag)) }
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(tag, style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(DetailEvent.AddToLibrary(
                            tags = selectedTags,
                            cover = cover
                        ))
                        onDismiss(DetailEvent.ToggleShowDialog)
                    },
                    enabled = selectedTags.isNotEmpty()
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss(DetailEvent.ToggleShowDialog) }) {
                    Text("取消")
                }
            },
            modifier = Modifier.heightIn(max = 500.dp)
        )
    }
}