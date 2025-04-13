package com.jadesoft.javhub.ui.detail

sealed class DetailEvent {
    data class LoadMovie(val movieCode: String) : DetailEvent()
    object ToggleShowViewer : DetailEvent()
    data class ToggleImageList(val images: List<String>) : DetailEvent()
    data class ToggleImageIndex(val index: Int) : DetailEvent()
    data class AddToLibrary(val tags: List<String>, val cover: String) : DetailEvent()
    object DeleteToLibrary : DetailEvent()
    data class AddToHistory(val cover: String) : DetailEvent()
    data class IsMovieExisted(val code: String) : DetailEvent()
    object InitState : DetailEvent()
    data class EditTags(val tag: String) : DetailEvent()
    object ToggleShowDialog : DetailEvent()
    data class VideoUrlInitialize(val code: String) : DetailEvent()
}