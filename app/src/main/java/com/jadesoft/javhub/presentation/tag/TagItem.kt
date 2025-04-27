package com.jadesoft.javhub.presentation.tag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Tag
import com.jadesoft.javhub.ui.tag.DialogType
import com.jadesoft.javhub.ui.tag.TagEvent

@Composable
fun TagItem(
    tag: Tag,
    onEdit: (TagEvent.ShowDialog) -> Unit,
    onDelete: (TagEvent.ShowDialog) -> Unit,
    onMoveUp: (TagEvent.OnMoveUpTag) -> Unit,
    onMoveDown: (TagEvent.OnMoveDownTag) -> Unit
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(Icons.Default.DragHandle, "drag", Modifier.padding(end = 16.dp))
//            Text(tag.name, Modifier.weight(1f))
//
//            IconButton(onClick = {
//                onEdit(TagEvent.ShowDialog(DialogType.Edit(tag.index)))
//            }) {
//                Icon(Icons.Outlined.Edit, "edit")
//            }
//            IconButton(onClick = {
//                onDelete(TagEvent.ShowDialog(DialogType.Delete(tag.index)))
//            }) {
//                Icon(Icons.Outlined.Delete, "delete")
//            }
//        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 6.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.BookmarkBorder, "", Modifier.padding(start = 12.dp, end = 16.dp))
                Text(tag.name)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onMoveUp(TagEvent.OnMoveUpTag(tag.index))
                }) {
                    Icon(Icons.Default.ArrowDropUp, "up")
                }
                IconButton(onClick = {
                    onMoveDown(TagEvent.OnMoveDownTag(tag.index))
                }) {
                    Icon(Icons.Default.ArrowDropDown, "down")
                }
                if (tag.name != "默认") {
                    Text("", Modifier.weight(1f))
                    IconButton(onClick = {
                        onEdit(TagEvent.ShowDialog(DialogType.Edit(tag.index)))
                    }) {
                        Icon(Icons.Outlined.Edit, "edit")
                    }
                    IconButton(onClick = {
                        onDelete(TagEvent.ShowDialog(DialogType.Delete(tag.index, tag.name)))
                    }) {
                        Icon(Icons.Outlined.Delete, "delete")
                    }
                }
            }
        }
    }
}