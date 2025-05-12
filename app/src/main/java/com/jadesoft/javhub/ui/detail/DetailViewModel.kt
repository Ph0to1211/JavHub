package com.jadesoft.javhub.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.db.dto.HistoryEntity
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.model.MovieDetail
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.data.repository.DetailRepository
import com.jadesoft.javhub.util.CommonUtils
import com.jadesoft.javhub.util.toEntity
import com.jadesoft.javhub.util.toHistoryEntity
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
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository,
    private val preferences: PreferencesManager
): ViewModel() {

    private val _detailState = MutableStateFlow(createDetailState())
    val detailState: StateFlow<DetailState> = _detailState

    init {
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            preferences.preferencesChangeFlow
                .collect {
                    _detailState.update { createDetailState() }
                }
        }
    }

    private fun createDetailState(): DetailState {
        val tags = stringToList(preferences.userTags)
        return DetailState(
            censoredType = preferences.showUncensored,
            isStealth = preferences.stealthMode,
            tags = tags,
            selectedTags = stringToList(tags.firstOrNull() ?: "") // 安全处理空值
        )
    }

    private var daoJob: Job? = null
    private var checkJob: Job? = null

    private val _snackbarEvent = Channel<String>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.LoadMovie -> handelLoadMovie(event.movieCode)
            is DetailEvent.ToggleShowViewer -> handleToggleShowViewer()
            is DetailEvent.ToggleImageList -> handleToggleImageList(event.images)
            is DetailEvent.ToggleImageIndex -> handleToggleImageIndex(event.index)
            is DetailEvent.AddToLibrary -> handelAddToLibrary(event.tags, event.cover)
            is DetailEvent.DeleteToLibrary -> handleDeleteFromLibrary()
            is DetailEvent.AddToHistory -> handleAddToHistory(event.cover)
            is DetailEvent.InitState -> handleInitState()
            is DetailEvent.EditTags -> handleEditTags(event.tag)
            is DetailEvent.ToggleShowDialog -> handleToggleShowDialog()
            is DetailEvent.VideoUrlInitialize -> handleVideoUrlInitialize(event.code)
        }
    }

    private fun handelLoadMovie(movieCode: String) {
        updateState { copy(isLoading = true) }
        viewModelScope.launch {
            val res: MovieDetail = repository.getMovieDetail(movieCode)
            updateState { copy(
                movie = res,
                isLoading = false
            ) }
            checkFollowed()
        }
    }

    private fun handleVideoUrlInitialize(code: String) {
        if (_detailState.value.isVideoUrlInitialized) return
        viewModelScope.launch {
            val videoUrls = CommonUtils.generateVideoUrls(code)
            CommonUtils.findFirstValidLink(videoUrls) { validUrl ->
                if (validUrl.isNotEmpty()) {
                    updateState { copy(videoUrl = validUrl) }
                }
            }
            updateState { copy(isVideoUrlInitialized = true) }
        }
    }

    private fun handleToggleShowViewer() {
        updateState { copy(showImageViewer = !showImageViewer) }
    }

    private fun handleToggleImageList(images: List<String>) {
        updateState { copy(imageList = images) }
    }

    private fun handleToggleImageIndex(index: Int) {
        updateState { copy(currentImageIndex = index) }
    }

    private fun handelAddToLibrary(tags: List<String>, cover: String) {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val createDate =  current.format(formatter)
            val tag = listToString(tags)
            val movie: MovieEntity = Movie(
                code = _detailState.value.movie?.code ?: "",
                censored = _detailState.value.movie?.censored ?: true,
                title = _detailState.value.movie?.title ?: "",
                cover = _detailState.value.movie?.bigCover ?: "",
            ).toEntity(tag = tag, cover = cover, createDate = createDate)
            repository.AddToLibrary(movie)
            updateState { copy(
                isAdded = true,
                isUserAction = true
            ) }

            checkFollowed()
            _snackbarEvent.send("收藏成功～")
        }
    }

    private fun handleDeleteFromLibrary() {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            repository.DeleteFromLibrary(_detailState.value.movie?.code ?: "")
            updateState { copy(
                isAdded = false,
                isUserAction = true
            ) }

            checkFollowed()
            _snackbarEvent.send("取消收藏成功")
        }
    }

    private fun handleAddToHistory(cover: String) {
        daoJob?.cancel()
        daoJob = viewModelScope.launch {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val createDate =  current.format(formatter)
            val movie: HistoryEntity = Movie(
                code = _detailState.value.movie?.code ?: "",
                censored = _detailState.value.movie?.censored ?: true,
                title = _detailState.value.movie?.title ?: "",
                cover = _detailState.value.movie?.bigCover ?: "",
            ).toHistoryEntity(cover, createDate)
            repository.AddToHistory(movie)
        }
    }

    private fun checkFollowed() {
        if (_detailState.value.isChecking) return
        updateState { copy(isChecking = true) }

        checkJob?.cancel()
        checkJob = viewModelScope.launch {
            val isAdded = repository.isMovieExists(_detailState.value.movie?.code ?: "")
            updateState { copy(
                isAdded = isAdded,
                isChecking = false
            ) }
        }
    }

    private fun handleInitState() {
        updateState { copy(isUserAction = true) }
    }

    private fun handleEditTags(tag: String) {
        val newTags = _detailState.value.selectedTags.toMutableList().apply {
            if (contains(tag)) remove(tag) else add(tag)
        }
        updateState { copy(selectedTags = newTags) }
    }

    private fun handleToggleShowDialog() {
        updateState { copy(showDialog = !showDialog) }
    }

    /* 辅助函数 */
    // region 工具方法
    private inline fun updateState(transform: DetailState.() -> DetailState) {
        _detailState.update(transform)
    }

    private fun stringToList(input: String): List<String> {
        return input.split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }
    }

    private fun listToString(input: List<String>): String {
        return input.joinToString(",")
    }

}