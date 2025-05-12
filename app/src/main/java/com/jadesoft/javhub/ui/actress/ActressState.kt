package com.jadesoft.javhub.ui.actress

import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie

data class ActressState(
    val actress: Actress = Actress(
        code = "",
        name = "",
        avatar = "",
        censored = false
    ),
    val movies: List<Movie> = emptyList(),
    val censored: Boolean,
    val onlyShowMag: Boolean,
    val itemStyle: Int,
    val itemNum: Int,
    val isBlurred: Boolean,
    val isLoadingActress: Boolean = false,
    val isLoadingMovie: Boolean = false,
    val isChecking: Boolean = false,
    val pagination: Pagination = Pagination(),
    val isFollowed: Boolean = false
)

data class Pagination(
    val page: Int = 1,
    val hasMore: Boolean = true
)