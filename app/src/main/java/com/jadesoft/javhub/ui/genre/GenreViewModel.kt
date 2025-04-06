package com.jadesoft.javhub.ui.genre

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val genreRepository: GenreRepository,
    private val preferences: PreferencesManager
): ViewModel() {

    var scrollState by mutableStateOf(LazyGridState())
        private set

    private val _items = MutableStateFlow<List<Movie>>(emptyList())
    val items: StateFlow<List<Movie>> = _items

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _showUncensored = MutableStateFlow<Boolean>(false)
    val showUncensored: StateFlow<Boolean> = _showUncensored

    private val _onlyShowMag = MutableStateFlow<Boolean>(preferences.onlyShowMag)
    val onlyShowMag: StateFlow<Boolean> = _onlyShowMag

    private var _page = MutableStateFlow<Int>(1)
    val page = _page

    private val _hasMore = MutableStateFlow<Boolean>(true)
    val hasMore = _hasMore

    private val _itemStyle = MutableStateFlow<Int>(preferences.itemStyle)
    val itemStyle: StateFlow<Int> = _itemStyle

    fun getData(code: String, type: Boolean) {
        if (_isLoading.value || !_hasMore.value) return
        _isLoading.value = true

        viewModelScope.launch {
            val data = genreRepository.getData(
                code = code,
                type = type,
                onlyShowMag = _onlyShowMag.value,
                page = _page.value
            )
            if (data.isEmpty()) _hasMore.value = false else _items.value += data
            _page.value++
            _isLoading.value = false
        }
    }

    fun refreshData(code: String, type: Boolean) {
        _items.value = emptyList()
        _page.value = 1
        _hasMore.value = true
        getData(code, type)
    }
}