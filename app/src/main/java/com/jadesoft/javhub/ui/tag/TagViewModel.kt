package com.jadesoft.javhub.ui.tag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.model.Tag
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val repository: TagRepository,
    private val preferences: PreferencesManager
): ViewModel() {

    private val _tagState = MutableStateFlow(
        TagState(
            originTag = preferences.userTags,
            tags = tagsTransformer(stringToList(preferences.userTags))
        )
    )
    val tagState: StateFlow<TagState> = _tagState


    fun onEvent(event: TagEvent) {
        when(event) {
            is TagEvent.ShowDialog -> handleShowDialog(event.type)
            is TagEvent.DismissDialog -> updateState { copy(currentDialog = null) }
            is TagEvent.OnAddTag -> {
                handleAddTag(event.name)
                updateState { copy(currentDialog = null) }
            }
            is TagEvent.OnEditTag -> {
                handleEditTag(event.index, event.name)
                updateState { copy(currentDialog = null) }
            }
            is TagEvent.OnDeleteTag -> {
                handleDeleteTag(event.index, event.name)
                updateState { copy(currentDialog = null) }
            }
            is TagEvent.OnMoveUpTag -> {
                handleMoveUpTag(event.index)
            }
            is TagEvent.OnMoveDownTag -> {
                handleMoveDownTag(event.index)
            }
        }
    }

    private fun handleShowDialog(type: DialogType) {
        updateState { copy(currentDialog = type) }
    }

    private fun handleAddTag(name: String) {
        val currentTags = _tagState.value.tags

        if (name.isBlank()) return
        if (currentTags.any { it.name.equals(name, ignoreCase = true) }) {
            return
        }

        val newIndex = currentTags.size + 1


        updateState {
            val newTags = tags + Tag(newIndex, name)
            copy(
                originTag = newTags.joinToString(",") { it.name },
                tags = newTags
            )
        }
        preferences.userTags = _tagState.value.originTag
    }

    private fun handleEditTag(targetIndex: Int, newName: String) {
        if (newName.isBlank()) return

        val currentTags = _tagState.value.tags

        if (currentTags.any { it.name.equals(newName, ignoreCase = true) && it.index != targetIndex }) return

        updateState {
            val updatedTags = tags.map { tag ->
                if (tag.index == targetIndex) tag.copy(name = newName) else tag
            }
            copy(
                originTag = updatedTags.joinToString(",") { it.name },
                tags = updatedTags
            )
        }
        preferences.userTags = _tagState.value.originTag
    }

    private fun handleDeleteTag(targetIndex: Int, name: String) {
        updateState {
            val filteredTags = tags.filterNot { it.index == targetIndex }
            val reindexedTags = filteredTags.mapIndexed { idx, tag ->
                tag.copy(index = idx + 1)
            }
            copy(
                originTag = reindexedTags.joinToString(",") { it.name },
                tags = reindexedTags
            )
        }
        preferences.userTags = _tagState.value.originTag
        viewModelScope.launch {
            repository.replaceTag(name)
        }
    }

    private fun handleMoveUpTag(targetIndex: Int) {
        val currentTags = _tagState.value.tags.toMutableList()
        val position = currentTags.indexOfFirst { it.index == targetIndex }

        if (position > 0) {
            Collections.swap(currentTags, position, position - 1)
            val newTags = currentTags.mapIndexed { index, tag ->
                tag.copy(index = index + 1)
            }
            updateState {
                copy(
                    originTag = newTags.joinToString(",") { it.name },
                    tags = newTags
                )
            }
            preferences.userTags = _tagState.value.originTag
        }
    }

    private fun handleMoveDownTag(targetIndex: Int) {
        val currentTags = _tagState.value.tags.toMutableList()
        val position = currentTags.indexOfFirst { it.index == targetIndex }

        if (position < currentTags.size - 1) {
            Collections.swap(currentTags, position, position + 1)
            val newTags = currentTags.mapIndexed { index, tag ->
                tag.copy(index = index + 1)
            }
            updateState {
                copy(
                    originTag = newTags.joinToString(",") { it.name },
                    tags = newTags
                )
            }
            preferences.userTags = _tagState.value.originTag
        }
    }

    private fun tagsTransformer(tags: List<String>): List<Tag> {
        var tagsProcessed: List<Tag> = mutableListOf()
        tags.forEachIndexed(){ index, tag ->
            tagsProcessed = tagsProcessed + Tag(index, tag)
        }
        return tagsProcessed
    }

    private fun stringToList(input: String): List<String> {
        return input.split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }


    private inline fun updateState(transform: TagState.() -> TagState) {
        _tagState.update(transform)
    }
}