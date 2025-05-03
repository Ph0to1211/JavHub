package com.jadesoft.javhub.ui.movie

import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie

data class MovieState(
    val items: List<Movie> = emptyList(),
    val actress: Actress = Actress("", "", "", censored = true),
    val isLoading: Boolean = false,
    val showUncensored: Boolean = false,
    val onlyShowMag: Boolean = false,
    val itemStyle: Int = 0,
    val itemNum: Int = 0,
    val isBlurred: Boolean = false,
    val pagination: Pagination = Pagination(),
    val error: String? = null
)

data class Pagination(
    val page: Int = 1,
    val hasMore: Boolean = true
)
