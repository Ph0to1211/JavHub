package com.jadesoft.javhub.ui.actress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.db.dto.ActressEntity
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.ActressRepository
import com.jadesoft.javhub.util.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ActressViewModel @Inject constructor(
    private val actressRepository: ActressRepository,
    private val preferences: PreferencesManager
): ViewModel() {

    private val _actressState = MutableStateFlow(
        ActressState(
            censored = preferences.showUncensored,
            onlyShowMag = preferences.onlyShowMag,
            itemStyle = preferences.itemStyle,
            itemNum = preferences.itemNum,
            isBlurred = preferences.publicMode
        )
    )
    val actressState: StateFlow<ActressState> = _actressState

    private var daoJob: Job? = null
    private var checkJob: Job? = null

    private val _snackbarEvent = Channel<String>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    fun onEvent(event: ActressEvent) {
        when(event) {
            is ActressEvent.OnAddFollow -> handleAddFollow()
            is ActressEvent.OnRemoveFollow -> handleRemoveFollow()
        }
    }

    fun loadActress(censored: Boolean, code: String) {
        if (_actressState.value.isLoadingActress) return
        updateState { copy(isLoadingActress = true) }

        viewModelScope.launch {
            val actress = actressRepository.getActress(
                censored = censored,
                code = code
            )
            updateState { copy(
                actress = actress,
                isLoadingActress = false
            ) }
            checkFollowed()
        }
    }

    fun loadMovies(censored: Boolean, code: String) {
        if (_actressState.value.isLoadingMovie || !_actressState.value.pagination.hasMore) return
        updateState { copy(isLoadingMovie = true) }

        viewModelScope.launch {
            val data = actressRepository.getMovies(
                censored = censored,
                code = code,
                page = _actressState.value.pagination.page
            )
            updateState { copy(
                movies = movies + data,
                pagination = pagination.copy(
                    page = pagination.page + 1,
                    hasMore = data.isNotEmpty()
                ),
                isLoadingMovie = false
            ) }
        }
    }

    private fun handleAddFollow() {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val createDate =  current.format(formatter)

            val actress: ActressEntity = _actressState.value.actress.toEntity(createDate)
            actressRepository.addFollow(actress)
            updateState { copy(isFollowed = true) }
            checkFollowed()

            if (_actressState.value.isFollowed) {
                _snackbarEvent.send("已关注 ${_actressState.value.actress.name}")
            }
        }
    }

    private fun handleRemoveFollow() {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            actressRepository.removeFollow(_actressState.value.actress.code)
            updateState { copy(isFollowed = false) }
            checkFollowed()

            if (!_actressState.value.isFollowed) {
                _snackbarEvent.send("已取消关注 ${_actressState.value.actress.name}")
            }
        }
    }

    private fun checkFollowed() {
        if (_actressState.value.isChecking) return
        updateState { copy(isChecking = true) }

        checkJob?.cancel()
        checkJob = viewModelScope.launch {
            val isFollowed = actressRepository.checkFollowed(_actressState.value.actress.code)
            updateState { copy(
                isFollowed = isFollowed,
                isChecking = false
            ) }
        }
    }


    private inline fun updateState(transform: ActressState.() -> ActressState) {
        _actressState.update(transform)
    }

}