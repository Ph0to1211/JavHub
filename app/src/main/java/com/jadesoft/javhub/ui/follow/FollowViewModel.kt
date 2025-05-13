package com.jadesoft.javhub.ui.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.repository.FollowRepository
import com.jadesoft.javhub.util.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val repository: FollowRepository,
): ViewModel() {

    private val _followState = MutableStateFlow(
        FollowState()
    )
    val followState: StateFlow<FollowState> = _followState

    private var loadJob: Job? = null
    private var daoJob: Job? = null

    fun onEvent(event: FollowEvent) {
        when(event) {
            is FollowEvent.OnRemoveFollow -> handleRemoveFollow()
            is FollowEvent.ToggleShowDialog -> handleToggleShowDialog(event.code)
            is FollowEvent.ToggleMenuExpanded -> handleToggleMenuExpanded()
            is FollowEvent.ToggleSortType -> handleToggleSortType(event.type)
        }
    }

    fun loadItems() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val res = repository.getFollowing()
            val items = sortItems(res.map { it.toModel() })
            println(items)
            updateState { copy(
                followings = items,
            ) }
        }
    }

    private fun handleRemoveFollow() {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            _followState.value.selectedCode?.let { repository.deleteFollowing(it) }
            loadItems()
        }
    }

    private fun handleToggleShowDialog(code: String?) {
        updateState {
            copy(
                isShowDialog = code != null,
                selectedCode = code
            )
        }
    }

    private fun handleToggleMenuExpanded() {
        updateState { copy(isMenuExpanded = !isMenuExpanded) }
    }

    private fun handleToggleSortType(type: SortType) {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            updateState { copy(currentSort = type) }
            loadItems()
        }
    }

    private fun sortItems(items: List<Actress>): List<Actress> {
        return when(_followState.value.currentSort) {
            SortType.DATE_DESC -> items.reversed()
            SortType.DATE_ASC -> items
            SortType.NAME_DESC -> items.sortedByDescending { it.name.lowercase() }
            SortType.NAME_ASC -> items.sortedBy { it.name.lowercase() }
        }
    }


    private inline fun updateState(transform: FollowState.() -> FollowState) {
        _followState.update(transform)
    }
}