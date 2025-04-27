package com.jadesoft.javhub.ui.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.LibraryRepository
import com.jadesoft.javhub.util.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository,
    private val preferences: PreferencesManager
) : ViewModel() {

    private val _libraryState = MutableStateFlow(createLibraryState())
    val libraryState: StateFlow<LibraryState> = _libraryState

    init {
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            preferences.preferencesChangeFlow
                .collect {
                    _libraryState.update { createLibraryState() }
                }
        }
    }

    private fun createLibraryState(): LibraryState {
        return LibraryState(
            itemStyle = preferences.itemStyle,
            itemNum = preferences.itemNum,
            isBlurred = preferences.publicMode,
            tags = stringToList(preferences.userTags)
        )
    }

    var loadJob: Job? = null

    fun onEvent(event: LibraryEvent) {
        when(event) {
            is LibraryEvent.LoadItems -> handleLoadItems()
        }
    }

    fun handleLoadItems() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val items = repository.getAllMovies()
            updateState { copy(
                movies = mapMovies(items),
                count = items.size
            ) }
        }
    }

    private inline fun updateState(transform: LibraryState.() -> LibraryState) {
        _libraryState.update(transform)
    }

    private fun mapMovies(movies: List<MovieEntity>): Map<String, List<Movie>> {
        val result = mutableMapOf<String, MutableList<Movie>>()

        movies.forEach { entity ->
            val tags = stringToList(entity.tag).takeIf { it.isNotEmpty() }
                ?: listOf("未分类") // 空标签处理

            val movie = entity.toModel()

            tags.forEach { tag ->
                val list = result.getOrPut(tag) { mutableListOf() }
                list.add(movie)
            }
        }

        return result
    }

    private fun stringToList(input: String): List<String> {
        return input.split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }
}