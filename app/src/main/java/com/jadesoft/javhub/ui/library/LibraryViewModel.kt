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
            is LibraryEvent.DeleteItems -> handleDeleteItems()
            is LibraryEvent.OnSelect -> handleSelect(event.movie)
            is LibraryEvent.OnUnSelect -> handleUnSelect()
            is LibraryEvent.OnSelectAll -> handleSelectAll()
            is LibraryEvent.OnReverseSelect -> handleReverseSelect()
            is LibraryEvent.OnToggleShowDialog -> handleToggleShowDialog()
        }
    }

    private fun handleLoadItems() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val items = repository.getAllMovies()
            updateState { copy(
                movies = mapMovies(items),
                count = items.size
            ) }
        }
    }

    private fun handleDeleteItems() {
        viewModelScope.launch {
            val list = _libraryState.value.selectedMovies.map { it.code }
            repository.deleteMovies(list)
            handleLoadItems()
            handleUnSelect()
        }
    }

    private fun handleSelect(movie: Movie) {
        updateState { copy(
            selectedMovies = if (selectedMovies.contains(movie)) {
                selectedMovies - movie
            } else {
                selectedMovies + movie
            }
        ) }
    }

    private fun handleUnSelect() {
        updateState { copy(
            selectedMovies = emptySet()
        ) }
    }

    private fun handleSelectAll() {
        updateState { copy(
            selectedMovies = selectedMovies + currentMovies
        ) }
    }


    private fun handleReverseSelect() {
        updateState {
            val currentPageSet = currentMovies.toSet()
            val newSelected = selectedMovies
                .minus(currentPageSet)
                .union(currentPageSet.minus(selectedMovies))

            copy(selectedMovies = newSelected)
        }
    }

    private fun handleToggleShowDialog() {
        updateState { copy(
            showDialog = !showDialog
        ) }
    }

    fun updateCurrentMovies(movies: List<Movie>) {
        updateState { copy(
            currentMovies = movies
        ) }
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