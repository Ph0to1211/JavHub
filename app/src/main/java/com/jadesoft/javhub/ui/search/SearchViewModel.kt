package com.jadesoft.javhub.ui.search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.SearchRepository
import com.jadesoft.javhub.ui.explore.ExploreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val preferences: PreferencesManager
): ViewModel() {

    private val _searchState = MutableStateFlow(
        SearchState(
            onlyShowMag = preferences.onlyShowMag,
            itemStyle = preferences.itemStyle,
            itemNum = preferences.itemNum,
            isBlurred = preferences.publicMode
        )
    )
    val searchState: StateFlow<SearchState> = _searchState

    var scrollState by mutableStateOf(LazyGridState())
        private set

    private var searchJob: Job? = null
    private var loadJob: Job? = null


    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.ValueChange -> handleValueChange(event.text)
            is SearchEvent.ClearQuery -> handleClearQuery()
            is SearchEvent.LoadItems -> handleLoadItems()
            is SearchEvent.SubmitSearch -> handleSubmitSearch()
            is SearchEvent.ToggleMag -> handleToggleMag()
            is SearchEvent.RefreshData -> handleRefreshData()
            is SearchEvent.RefreshFilter -> handleRefreshFilter()
            is SearchEvent.ModifierType -> handleModifierType(event.index)
        }
    }

    private fun handleValueChange(text: String) {
        searchJob?.cancel()
        updateState { copy(searchQuery = text) }
        searchJob = viewModelScope.launch {
            delay(500) // 防抖延迟500ms
            if (text.isNotEmpty()) {
                handleSubmitSearch() // 自动触发搜索
            }
        }

    }

    private fun handleClearQuery() {
        updateState { copy(
            searchQuery = "",
            showResult = false,
            items = emptyList(),
            pagination = pagination.copy(page = 1, hasMore = true)
        ) }
        triggerRefreshFilter()
    }

    private fun handleLoadItems() {
        if (_searchState.value.isLoading || !_searchState.value.pagination.hasMore) return
        loadJob?.cancel()
        updateState { copy(isLoading = true) }

        loadJob = viewModelScope.launch {
            val data = searchRepository.search(
                query = _searchState.value.searchQuery,
                showUncensored = _searchState.value.showUncensored,
                onlyShowMag = _searchState.value.onlyShowMag,
                page = _searchState.value.pagination.page,
                type = _searchState.value.type
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

    private fun handleSubmitSearch() {
        triggerRefreshData()
        triggerRefreshFilter()
        updateState { copy(showResult = true) }
    }

    private fun handleToggleMag() {
        updateState { copy(onlyShowMag = !onlyShowMag) }
        preferences.onlyShowMag = _searchState.value.onlyShowMag
        triggerRefreshData()
    }

    private fun handleRefreshData() {
        updateState { copy(
            items = emptyList(),
            pagination = pagination.copy(page = 1, hasMore = true)
        ) }
        handleLoadItems()
    }

    private fun handleRefreshFilter() {
        updateState { copy(
            type = 1,
            showUncensored = false,
            selectedIndex = 0
        ) }
    }

    private fun handleModifierType(index: Int){
        updateState { copy(selectedIndex = index) }
        when (index) {
            0 -> {
                updateState { copy(
                    type = 1,
                    showUncensored = false
                ) }
            }
            1 -> {
                updateState { copy(
                    type = 1,
                    showUncensored = true
                ) }
            }
            2 -> { }
            3 -> {
                updateState { copy(
                    type = 2,
                    showUncensored = false
                ) }
            }
            4 -> {
                updateState { copy(
                    type = 3,
                    showUncensored = false
                ) }
            }
            5 -> {
                updateState { copy(
                    type = 4,
                    showUncensored = false
                ) }
            }
            6 -> {
                updateState { copy(
                    type = 5,
                    showUncensored = false
                ) }
            }
        }
        triggerRefreshData()
    }

    /* 辅助函数 */
    // region 工具方法
    private inline fun updateState(transform: SearchState.() -> SearchState) {
        _searchState.update(transform)
    }

    private fun triggerRefreshData() {
        handleRefreshData()
    }

    private fun triggerRefreshFilter() {
        handleRefreshFilter()
    }

    // 退出时销毁协程任务
    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
        loadJob?.cancel()
    }

}