package com.jadesoft.javhub.ui.explore

import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.data.model.Movie

data class ExploreState(
    val items: List<ExploreItem> = emptyList(),
    val movies: List<Movie> = emptyList(),
    val actress: List<Actress> = emptyList(),
    val isLoadingItem: Boolean = false,
    val isLoadingGenre: Boolean = false,
    val pagination: Pagination = Pagination(),
    val exploreType: Boolean,
    val showUncensored: Boolean,
    val onlyShowMag: Boolean,
    val itemStyle: Int,
    val isBlurred: Boolean = false,
    val itemNum: Int,
    val genres: Map<String, List<Genre>> = emptyMap(),
    val filterOptions: List<Genre> = emptyList(),
    val filterCode: String = "",
    val filterName: String = "",
    val isFiltered: Boolean = false,
    val showFilter: Boolean = false,
    val showMenu: Boolean = false
)

sealed class ExploreItem {
    data class MovieItem(val data: Movie) : ExploreItem()
    data class ActressItem(val data: Actress) : ExploreItem()
}

data class Pagination(
    val page: Int = 1,
    val hasMore: Boolean = true
)