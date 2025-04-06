package com.jadesoft.javhub.ui.search

import com.jadesoft.javhub.data.model.Movie

data class SearchState(
    val showResult: Boolean = false,
    val items: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val showUncensored: Boolean = false,
    val onlyShowMag: Boolean = false,
    val pagination: Pagination = Pagination(),
    val itemStyle: Int = 0,
    val itemNum: Int = 0,
    val isBlurred: Boolean = false,
    val searchQuery: String = "",
    val type: Int = 1,
    val selectedIndex: Int = 0
)

data class Pagination(
    val page: Int = 1,
    val hasMore: Boolean = true
)