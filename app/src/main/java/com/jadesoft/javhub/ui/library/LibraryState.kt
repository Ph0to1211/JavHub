package com.jadesoft.javhub.ui.library

import com.jadesoft.javhub.data.model.Movie

data class LibraryState(
    val movies: Map<String, List<Movie>> = emptyMap(),
    val tags: List<String> = emptyList(),
    val count: Int = 1,
    val itemStyle: Int,
    val itemNum: Int,
    val isBlurred: Boolean = false
)