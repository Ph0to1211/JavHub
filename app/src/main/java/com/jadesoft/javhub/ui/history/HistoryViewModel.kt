package com.jadesoft.javhub.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.model.History
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.HistoryRepository
import com.jadesoft.javhub.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: HistoryRepository,
    private val homeRepository: HomeRepository,
    private val preferences: PreferencesManager
): ViewModel() {

    private val _historyState = MutableStateFlow(
        HistoryState(
            count = 0,
            isBlurred = preferences.publicMode
        )
    )
    val historyState: StateFlow<HistoryState> = _historyState

    var daoJob: Job? = null

    fun onEvent(event: HistoryEvent) {
        when(event) {
            is HistoryEvent.GetItems -> handleGetItems()
            is HistoryEvent.DeleteAllHistory -> handleDeleteAllHistory()
            is HistoryEvent.ToggleShowDialog -> handleToggleShowDialog()
            is HistoryEvent.ToggleDrawerOpen -> handleToggleDrawerOpen()
            is HistoryEvent.DeleteHistory -> handleDeleteHistory(event.code)
        }
    }

    private fun handleGetItems() {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
//            val items = repository.getHistory().map { it.toModel() }
            val items = repository.getHistory()
            val histories = convertToGroupedList(items)
            updateState { copy(
                histories = histories,
                count = items.size
            ) }
        }
    }

    private fun handleDeleteAllHistory() {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            repository.deleteAllHistory()
            handleGetItems()
        }
    }

    private fun handleToggleShowDialog() {
        updateState { copy(showDialog = !showDialog) }
    }

    private fun handleToggleDrawerOpen() {
        homeRepository.toggleDrawerOpen()
    }

    private fun handleDeleteHistory(code: String) {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            repository.deleteHistory(code)
            handleGetItems()
        }
    }

    fun convertToGroupedList(entities: List<HistoryEntity>): List<History> {
        val grouped = entities.groupBy { entity ->
            entity.createDate.substring(0, 10) // 提取日期部分（如 "2023-10-05"）
        }

        // 按日期降序排序（最近日期在前）
        val sortedDates = grouped.keys.sortedDescending()

        val result = mutableListOf<History>()

        for (date in sortedDates) {
            // 添加日期分隔项
            result.add(History.DateItem(date))

            // 添加该日期的所有历史记录项（按时间倒序）
            grouped[date]?.sortedByDescending { it.createDate }?.forEach { entity ->
                result.add(
                    History.MovieItem(
                        movie = Movie(
                            code = entity.code,
                            censored = entity.censored,
                            title = entity.title,
                            cover = entity.cover,
                        ),
                        time = entity.createDate.substring(11, 16)
                    )
                )
            }
        }
        return result
    }

    private inline fun updateState(transform: HistoryState.() -> HistoryState) {
        _historyState.update(transform)
    }
}