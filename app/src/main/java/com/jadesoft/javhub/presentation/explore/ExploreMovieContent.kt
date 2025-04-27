package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.model.MovieDetail
import com.jadesoft.javhub.navigation.MovieRoute
import com.jadesoft.javhub.presentation.common.MovieCard
import com.jadesoft.javhub.presentation.common.MovieCardWithBottomTitle
import com.jadesoft.javhub.presentation.common.MovieCardWithoutTitle
import com.jadesoft.javhub.presentation.common.MovieListItem
import com.jadesoft.javhub.presentation.common.NoDataTip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreMovieContent(
    movies: List<Movie>,
    isLoading: Boolean,
    scrollState: LazyGridState,
    navController: NavController,
    itemStyle: Int,
    isBlurred: Boolean,
    itemNum: Int,
    refreshData: () -> Unit
) {
    val state = rememberPullToRefreshState()

    val isList = remember(itemStyle) {
        when (itemStyle) {
            3 -> true
            else -> false
        }
    }

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = refreshData,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
                isRefreshing = false,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    ) {
        if (movies.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (!isList) itemNum else 1),
                state = scrollState,
                contentPadding = PaddingValues(
                    vertical = if (!isList) 10.dp else 5.dp,
                    horizontal = if (!isList) 10.dp else 0.dp
                ),
                verticalArrangement = Arrangement.spacedBy(if (!isList) 10.dp else 0.dp),
                horizontalArrangement = Arrangement.spacedBy(if (!isList) 10.dp else 0.dp)
            ) {
                itemsIndexed(movies) { index, movie ->
                    key(index) {
                        if (isList) {
                            MovieListItem(movie = movie, isBlurred = isBlurred, onClick = {
                                navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                            })
                        } else {
                            when(itemStyle) {
                                0 -> MovieCard(movie = movie, isBlurred = isBlurred, onClick = {
                                    navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                                })
                                1 -> MovieCardWithBottomTitle(movie = movie, isBlurred = isBlurred, onClick = {
                                    navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                                })
                                2 -> MovieCardWithoutTitle(movie = movie, isBlurred = isBlurred, onClick = {
                                    navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                                })
                            }
                        }
                    }
                }

                if (isLoading) {
                    item(span = { GridItemSpan(if (!isList) itemNum else 1) }) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                    }
                }
            }
        } else {
            NoDataTip("无结果", false)
        }
    }

}