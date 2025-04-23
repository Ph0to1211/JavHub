package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jadesoft.javhub.ui.detail.DetailEvent

@Composable
fun DetailDialog(
    cover: String,
    showDialog: Boolean,
    onDismiss: (DetailEvent.ToggleShowDialog) -> Unit,
    onConfirm: (DetailEvent.AddToLibrary) -> Unit,
    editTags: (DetailEvent.EditTags) -> Unit,
    tags: List<String>,
    selectedTags: List<String>,
    toAdd: () -> Unit
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onDismiss(DetailEvent.ToggleShowDialog)
            }
        ) {
            Surface(
                modifier = Modifier
                    .widthIn(max = 450.dp)
                    .heightIn(max = 450.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // 标题
                    Text("添加到库中", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(16.dp))

                    // 内容区域
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text("选择标签", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(12.dp))

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

                    // 按钮行
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // 新增的左侧按钮
                        TextButton(
                            onClick = {
                                toAdd()
                                onDismiss(DetailEvent.ToggleShowDialog)

                            }
                        ) {
                            Text("管理标签")
                        }

                        Row {
                            TextButton(
                                onClick = { onDismiss(DetailEvent.ToggleShowDialog) },
                                Modifier.padding(end = 6.dp)
                            ) {
                                Text("取消")
                            }
                            Button(
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
                        }
                    }
                }
            }
        }

    }
}