package com.jadesoft.javhub.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.navigation.MovieRoute
import com.jadesoft.javhub.presentation.common.ActressList
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.presentation.common.MovieCard
import com.jadesoft.javhub.presentation.common.MovieList
import com.jadesoft.javhub.presentation.common.NoDataTip
import com.jadesoft.javhub.ui.search.SearchEvent
import com.jadesoft.javhub.ui.search.SearchViewModel

@Composable
fun SearchResultContent(
    movies: List<Movie>,
    actress: List<Actress>,
    selectedIndex: Int,
    page: Int,
    isLoading: Boolean,
    hasMore: Boolean,
    itemStyle: Int,
    itemNum: Int,
    isBlurred: Boolean,
    scrollState: LazyGridState,
    getData: (SearchEvent.LoadItems) -> Unit,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        getData(
            SearchEvent.LoadItems
        )
    }

    LaunchedEffect(scrollState, movies) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == movies.size - 1
                    && !isLoading
                    && hasMore) {
                    getData(
                        SearchEvent.LoadItems
                    )
                }
            }
    }

    if (page != 1) {
        if (selectedIndex != 2) {
            MovieList(
                movies = movies,
                isLoading =  isLoading,
                navController = navController,
                scrollState = scrollState,
                itemStyle = itemStyle,
                itemNum = itemNum,
                isBlurred = isBlurred
            )
        } else {
            ActressList(
                actresses = actress,
                isLoading = isLoading,
                censoredType = false,
                scrollState = scrollState,
                navController = navController
            )
        }
    } else {
        CircularLoading()
    }
}