package com.jadesoft.javhub.ui.explore

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.ExploreRepository
import com.jadesoft.javhub.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: ExploreRepository,
    private val homeRepository: HomeRepository,
    private val preferences: PreferencesManager
) : ViewModel() {

    private val _exploreState = MutableStateFlow(
        ExploreState(
            exploreType = preferences.exploreType,
            showUncensored = preferences.showUncensored,
            onlyShowMag = preferences.onlyShowMag,
            itemStyle = preferences.itemStyle,
            isBlurred = preferences.publicMode,
            itemNum = preferences.itemNum
        )
    )
    val exploreState: StateFlow<ExploreState> = _exploreState

    var exploreScrollState by mutableStateOf(LazyGridState())
        private set

    // 统一事件处理入口
    fun onEvent(event: ExploreEvent) {
        when (event) {
            is ExploreEvent.ResetFilter -> handleResetFilter()
            is ExploreEvent.SubmitFilter -> handleSubmitFilter()
            is ExploreEvent.ToggleGenre -> handleToggleGenre()
            is ExploreEvent.ToggleFilter -> handleToggleFilter()
            is ExploreEvent.EditFilterOption -> handleEditFilter(event.genre)
            is ExploreEvent.ToggleItemStyle -> handleToggleItemStyle(event.option)
            is ExploreEvent.ToggleItemNum -> handleToggleItemNum(event.num)
            is ExploreEvent.ToggleMag -> handleToggleMag()
            is ExploreEvent.ToggleExploreType -> handleToggleExploreType(event.enabled)
            is ExploreEvent.ToggleMenu -> handleToggleMenu()
            is ExploreEvent.ToggleDrawerShow -> handleToggleDrawerOpen()
            is ExploreEvent.RefreshData -> handleRefreshData()
            is ExploreEvent.ScrollToTop -> handleScrollToTop()
            is ExploreEvent.LoadItems -> handleLoadItems()
            is ExploreEvent.LoadGenres -> handleLoadGenres()
        }
    }

    /* 具体事件处理逻辑 */
    // region 事件处理器
    private fun handleResetFilter() {
        updateState {
            copy(
                filterOptions = emptyList(),
                filterCode = "",
                filterName = "",
                isFiltered = false
            )
        }
        triggerRefresh()
    }

    private fun handleSubmitFilter() {
        val newFilterCode = _exploreState.value.filterOptions
            .joinToString("-") { it.code }

        val newFilterName = buildFilterName()

        updateState {
            copy(
                filterCode = newFilterCode,
                filterName = newFilterName,
                isFiltered = newFilterCode.isNotEmpty()
            )
        }
        triggerRefresh()
    }

    private fun handleToggleGenre() {
        updateState { copy(showUncensored = !showUncensored) }
        preferences.showUncensored = _exploreState.value.showUncensored
        handleResetFilter()
        handleLoadGenres()
    }

    private fun handleToggleFilter() {
        updateState { copy(showFilter = !showFilter) }
    }

    private fun handleEditFilter(genre: Genre) {
        val newOptions = _exploreState.value.filterOptions.toMutableList().apply {
            if (contains(genre)) remove(genre) else add(genre)
        }
        updateState { copy(filterOptions = newOptions) }
    }

    private fun handleToggleItemStyle(option: Int) {
        updateState { copy(itemStyle = option) }
        preferences.itemStyle = option
    }

    private fun handleToggleItemNum(num: Int) {
        updateState { copy(itemNum = num) }
        preferences.itemNum = num
    }

    private fun handleToggleMag() {
        updateState { copy(onlyShowMag = !onlyShowMag) }
        preferences.onlyShowMag = _exploreState.value.onlyShowMag
        triggerRefresh()
    }

    private fun handleToggleExploreType(enabled: Boolean) {
        updateState { copy(exploreType = enabled) }
        preferences.exploreType = enabled
        triggerRefresh()
    }

    private fun handleToggleMenu() {
        updateState { copy(showMenu = !showMenu) }
    }

    private fun handleToggleDrawerOpen() {
        homeRepository.toggleDrawerOpen()
    }

    private fun handleRefreshData() {
        updateState {
            copy(
                items = emptyList(),
                showFilter = false,
                pagination = pagination.copy(page = 1, hasMore = true)
            )
        }
        handleScrollToTop()
        handleLoadItems()
    }

    private fun handleScrollToTop() {
        viewModelScope.launch {
            exploreScrollState.scrollToItem(0)
        }
    }

    private fun handleLoadItems() {
        if (_exploreState.value.isLoadingItem || !_exploreState.value.pagination.hasMore) return

        updateState { copy(isLoadingItem = true) }

        viewModelScope.launch {
            try {
                val result = if (_exploreState.value.exploreType) {
                    loadMovies().map(ExploreItem::MovieItem)
                } else {
                    loadActresses().map(ExploreItem::ActressItem)
                }

                updateState {
                    copy(
                        items = items + result,
                        pagination = pagination.copy(
                            page = pagination.page + 1,
                            hasMore = result.isNotEmpty()
                        ),
                        isLoadingItem = false
                    )
                }
            } catch (e: Exception) {
                updateState { copy(isLoadingItem = false) }
                // 这里可以添加错误处理逻辑
            }
        }
    }

    private fun handleLoadGenres() {
        viewModelScope.launch {
            updateState { copy(isLoadingGenre = true) }
            val genres = repository.getGenres(_exploreState.value.showUncensored)
            updateState { copy(genres = genres, isLoadingGenre = false) }
        }
    }
    // endregion

    /* 辅助函数 */
    // region 工具方法
    private inline fun updateState(transform: ExploreState.() -> ExploreState) {
        _exploreState.update(transform)
    }

    private fun triggerRefresh() {
        handleRefreshData()
    }

    private fun buildFilterName(): String {
        return when (_exploreState.value.filterOptions.size) {
            0 -> ""
            1 -> _exploreState.value.filterOptions[0].subGenre
            2 -> "${_exploreState.value.filterOptions[0].subGenre}、${_exploreState.value.filterOptions[1].subGenre}"
            else -> "${_exploreState.value.filterOptions[0].subGenre}等${_exploreState.value.filterOptions.size}项"
        }
    }

    private suspend fun loadMovies(): List<Movie> {
        return if (_exploreState.value.isFiltered) {
            repository.getFilteredMovies(
                _exploreState.value.filterCode,
                _exploreState.value.showUncensored,
                _exploreState.value.onlyShowMag,
                _exploreState.value.pagination.page
            )
        } else {
            repository.getAllMovie(
                _exploreState.value.showUncensored,
                _exploreState.value.onlyShowMag,
                _exploreState.value.pagination.page
            )
        }
    }

    private suspend fun loadActresses(): List<Actress> {
        return repository.getAllActress(
            _exploreState.value.showUncensored,
            _exploreState.value.pagination.page
        )
    }
    // endregion
}

// 扩展事件类型定义
//sealed class ExploreEvent {
//    object ResetFilter : ExploreEvent()
//    object SubmitFilter : ExploreEvent()
//    object ToggleGenre : ExploreEvent()
//    object ToggleFilter : ExploreEvent()
//    data class EditFilterOption(val genre: Genre) : ExploreEvent()
//    data class ToggleItemStyle(val option: Int) : ExploreEvent()
//    object ToggleMag : ExploreEvent()
//    data class ToggleExploreType(val enabled: Boolean) : ExploreEvent()
//    object ToggleMenu : ExploreEvent()
//    object RefreshData : ExploreEvent()
//    object ScrollToTop : ExploreEvent()
//    object LoadItems : ExploreEvent()
//    object LoadGenres : ExploreEvent()
//}