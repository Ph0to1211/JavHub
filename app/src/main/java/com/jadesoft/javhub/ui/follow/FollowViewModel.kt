package com.jadesoft.javhub.ui.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var loadJob: Job? = null
    var daoJob: Job? = null

    fun onEvent(event: FollowEvent) {
        when(event) {
            is FollowEvent.OnRemoveFollow -> handleRemoveFollow()
            is FollowEvent.ToggleShowDialog -> handleToggleShowDialog(event.code)
        }
    }

    fun loadItems() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val items = repository.getFollowing()
            println("数据在这里哦\n${items}")
            updateState { copy(
                followings = items.map { it.toModel() },
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


    private inline fun updateState(transform: FollowState.() -> FollowState) {
        _followState.update(transform)
    }
}