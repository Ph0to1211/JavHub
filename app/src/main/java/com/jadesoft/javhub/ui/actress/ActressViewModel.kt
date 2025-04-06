package com.jadesoft.javhub.ui.actress

import androidx.lifecycle.ViewModel
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.ActressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ActressViewModel @Inject constructor(
    actressRepository: ActressRepository,
    private val preferences: PreferencesManager
): ViewModel() {

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
}