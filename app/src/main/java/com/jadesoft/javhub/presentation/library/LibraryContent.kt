package com.jadesoft.javhub.presentation.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.db.dto.MovieEntity
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.presentation.common.MovieList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryContent(
    movies: Map<String, List<Movie>>,
    scrollState: LazyGridState,
    navController: NavController,
    itemStyle: Int,
    itemNum: Int,
    isBlurred: Boolean,
    tags: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope
) {
    Column(Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f),
            edgePadding = 16.dp,
            divider = {}
        ) {
            tags.forEachIndexed { index, tag ->
                Tab(
                    text = { Text(tag) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }
        HorizontalDivider()
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val currentTag = tags.getOrNull(page) ?: ""
            val currentMovies = movies[currentTag] ?: emptyList()

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                MovieList(
                    movies = currentMovies,
                    isLoading = false,
                    scrollState = scrollState,
                    navController = navController,
                    itemStyle = itemStyle,
                    itemNum = itemNum,
                    isBlurred = isBlurred
                )
            }
        }
    }
}