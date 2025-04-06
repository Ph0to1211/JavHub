package com.jadesoft.javhub.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.MovieRepository
import com.jadesoft.javhub.ui.search.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    preferences: PreferencesManager
): ViewModel() {

    private val _movieState = MutableStateFlow(
        MovieState(
            onlyShowMag = preferences.onlyShowMag,
            itemStyle = preferences.itemStyle,
            itemNum = preferences.itemNum,
            isBlurred = preferences.publicMode
        )
    )
    val movieState:StateFlow<MovieState> = _movieState


    fun onEvent(event: MovieEvent) {
        when(event) {
            is MovieEvent.LoadItems -> handleLoadItems(
                event.code, event.censoredType, event.listType
            )
        }
    }

    private fun handleLoadItems(code: String, censoredType: Boolean, listType: ListType) {
        if (_movieState.value.isLoading || !_movieState.value.pagination.hasMore) return
        updateState { copy(isLoading = true) }

        viewModelScope.launch {
            val data = movieRepository.getData(
                code = code,
                listType = listType,
                censoredType = censoredType,
                onlyShowMag = _movieState.value.onlyShowMag,
                page = _movieState.value.pagination.page
            )
            updateState { copy(
                items = items + data,
                pagination = pagination.copy(
                    page = pagination.page + 1,
                    hasMore = data.isNotEmpty()
                ),
                isLoading = false
            ) }
        }
    }

    /* 辅助函数 */
    // region 工具方法
    private inline fun updateState(transform: MovieState.() -> MovieState) {
        _movieState.update(transform)
    }

}