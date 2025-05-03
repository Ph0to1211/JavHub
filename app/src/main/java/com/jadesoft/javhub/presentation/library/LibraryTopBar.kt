package com.jadesoft.javhub.presentation.library

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.SelectAll
import androidx.compose.material.icons.outlined.TabUnselected
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
import com.jadesoft.javhub.ui.library.LibraryEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryTopBar(
    selectedItemCount: Int,
    onUnSelect: (LibraryEvent.OnUnSelect) -> Unit,
    onSelectAll: (LibraryEvent.OnSelectAll) -> Unit,
    onReverseSelect: (LibraryEvent.OnReverseSelect) -> Unit,
    onToggleShowDialog: (LibraryEvent.OnToggleShowDialog) -> Unit,
) {
    val hasSelectedItem = selectedItemCount > 0
    TopAppBar(
        title = {
            if (hasSelectedItem) {
                Text(selectedItemCount.toString())
            } else {
                Text("书架")
            }
        },
        navigationIcon = {
            if (hasSelectedItem) {
                IconButton(
                    onClick = { onUnSelect(LibraryEvent.OnUnSelect) }
                ) {
                    Icon(Icons.Default.Clear, "clear")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = with(MaterialTheme.colorScheme) {
                surfaceColorAtElevation(if (hasSelectedItem) 3.dp else 0.dp)
            }
        ),
        actions = {
            Row {
                if (hasSelectedItem) {
//                    IconButton(
//                        onClick = {}
//                    ) {
//                        Icon(Icons.Outlined.BookmarkBorder, "tag")
//                    }
                    IconButton(
                        onClick = { onToggleShowDialog(LibraryEvent.OnToggleShowDialog) }
                    ) {
                        Icon(Icons.Outlined.DeleteOutline, "delete all")
                    }
                    IconButton(
                        onClick = { onSelectAll(LibraryEvent.OnSelectAll) }
                    ) {
                        Icon(Icons.Outlined.SelectAll, "select all")
                    }
                    IconButton(
                        onClick = { onReverseSelect(LibraryEvent.OnReverseSelect) }
                    ) {
                        Icon(Icons.Outlined.TabUnselected, "reverse select")
                    }
                }
            }
        }
    )
}