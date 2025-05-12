package com.jadesoft.javhub.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.navigation.MovieRoute

@Composable
fun SelectedMovieList(
    movies: List<Movie>,
    isLoading: Boolean,
    scrollState: LazyGridState,
    navController: NavController,
    itemStyle: Int,
    itemNum: Int,
    isBlurred: Boolean,
    onLongClickMovie: (Movie) -> Unit = {},
    selectedMovies: Set<Movie> = emptySet()
) {
    val isList = remember(itemStyle) {
        when (itemStyle) {
            3 -> true
            else -> false
        }
    }

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
                    val isSelected = selectedMovies.contains(movie)
                    if (isList) {
                        MovieListItem(
                            movie = movie,
                            isBlurred = isBlurred,
                            isSelected = isSelected,
                            onlySingleClick = selectedMovies.isNotEmpty(),
                            onClick = {
                                navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                            },
                            onLongClick = { onLongClickMovie(movie) }
                        )
                    } else {
                        when (itemStyle) {
                            0 -> MovieCard(
                                movie = movie,
                                isBlurred = isBlurred,
                                isSelected = isSelected,
                                onlySingleClick = selectedMovies.isNotEmpty(),
                                onClick = {
                                    navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                                },
                                onLongClick = { onLongClickMovie(movie) }
                            )
                            1 -> MovieCardWithBottomTitle(
                                movie = movie,
                                isBlurred = isBlurred,
                                isSelected = isSelected,
                                onlySingleClick = selectedMovies.isNotEmpty(),
                                onClick = {
                                    navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                                },
                                onLongClick = { onLongClickMovie(movie) }
                            )
                            2 -> MovieCardWithoutTitle(
                                movie = movie,
                                isBlurred = isBlurred,
                                isSelected = isSelected,
                                onlySingleClick = selectedMovies.isNotEmpty(),
                                onClick = {
                                    navController.navigate(MovieRoute(movie.code, movie.cover, movie.title))
                                },
                                onLongClick = { onLongClickMovie(movie) }
                            )
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