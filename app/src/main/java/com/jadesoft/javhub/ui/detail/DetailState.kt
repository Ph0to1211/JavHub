package com.jadesoft.javhub.ui.detail

import com.jadesoft.javhub.data.model.MovieDetail

data class DetailState(
    val movie: MovieDetail? = null,
    val isLoading: Boolean = false,
    val isAdded: Boolean = false,
    val censoredType: Boolean,
    val isStealth: Boolean = false,
    val showImageViewer: Boolean = false,
    val currentImageIndex: Int = 0,
    val imageList: List<String> = emptyList(),
    val isUserAction: Boolean = false,
    val tags: List<String> = emptyList(),
    val selectedTags: List<String> = emptyList(),
    val showDialog: Boolean = false
)