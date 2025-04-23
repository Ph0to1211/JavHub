package com.jadesoft.javhub.ui.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Tag
import com.jadesoft.javhub.presentation.tag.TagAddDialog
import com.jadesoft.javhub.presentation.tag.TagDeleteDialog
import com.jadesoft.javhub.presentation.tag.TagEditDialog
import com.jadesoft.javhub.presentation.tag.TagItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagScreen(
    navController: NavController,
    tagViewModel: TagViewModel = hiltViewModel<TagViewModel>()
) {

    val tagState = tagViewModel.tagState.collectAsState()

    val tags: List<Tag> = tagState.value.tags
    val currentDialog = tagState.value.currentDialog

    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("管理标签") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { tagViewModel.onEvent(TagEvent.ShowDialog(DialogType.Add)) }
            ) {
                Icon(Icons.Default.Add, "add", Modifier.padding(end = 6.dp))
                Text("添加标签")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            state = scrollState
        ) {
            items(tags) { tag ->
                TagItem(
                    tag = tag,
                    onEdit = tagViewModel::onEvent,
                    onDelete = tagViewModel::onEvent,
                    onMoveUp = tagViewModel::onEvent,
                    onMoveDown = tagViewModel::onEvent
                )
            }
        }

        when (currentDialog) {
            is DialogType.Add -> {
                TagAddDialog(
                    onDismiss = { tagViewModel.onEvent(TagEvent.DismissDialog) },
                    onConfirm = tagViewModel::onEvent
                )
            }
            is DialogType.Edit -> {
                val initialName = tags.find { it.index == currentDialog.index }?.name ?: ""
                TagEditDialog(
                    initialName = initialName,
                    onDismiss = { tagViewModel.onEvent(TagEvent.DismissDialog) },
                    onConfirm = { newName ->
                        tagViewModel.onEvent(TagEvent.OnEditTag(currentDialog.index, newName))
                    }
                )
            }
            is DialogType.Delete -> {
                TagDeleteDialog(
                    index = currentDialog.index,
                    onDismiss = { tagViewModel.onEvent(TagEvent.DismissDialog) },
                    onConfirm = {
                        tagViewModel.onEvent(TagEvent.OnDeleteTag(currentDialog.index, currentDialog.name))
                    }
                )
            }
            null -> Unit
        }
    }
}
