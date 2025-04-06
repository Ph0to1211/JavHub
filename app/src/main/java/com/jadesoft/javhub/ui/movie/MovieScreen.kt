package com.jadesoft.javhub.ui.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.presentation.common.MovieList
import com.jadesoft.javhub.presentation.common.MyTopAppBar

enum class ListType {
    Genre, Director, Producer, Publisher, Series, Actress, Null
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun MovieScreen(
    code: String,
    name: String,
    actressCode: String,
    censoredType: Boolean,
    listType: ListType,
    navController: NavController,
    movieViewModel: MovieViewModel = hiltViewModel<MovieViewModel>()
) {
    val scrollState = rememberLazyGridState()
    val isPullDown by derivedStateOf {
        scrollState.firstVisibleItemScrollOffset > 20
    }

    val movieState = movieViewModel.movieState.collectAsState()

    val movies = movieState.value.items
    val isLoading = movieState.value.isLoading
    val itemStyle = movieState.value.itemStyle
    val itemNum = movieState.value.itemNum
    val page = movieState.value.pagination.page
    val hasMore = movieState.value.pagination.hasMore
    val isBlurred = movieState.value.isBlurred

    LaunchedEffect(Unit) {
        movieViewModel.onEvent(MovieEvent.LoadItems(code, censoredType, listType))
    }

    LaunchedEffect(scrollState, movies) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == movies.size - 1
                    && !isLoading
                    && hasMore) {
                    movieViewModel.onEvent(MovieEvent.LoadItems(code, censoredType, listType))
                }
            }
    }

    Scaffold(
        topBar = { MyTopAppBar(
            navController = navController,
            isPullDown = isPullDown,
            title = name,
        ) },
    ) { innerPadding ->
        Column( Modifier.padding(innerPadding).fillMaxSize() ) {
            if (page != 1) {
                if (listType == ListType.Actress) {
//                    MovieActressInfoBar(actress)
                }
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
                CircularLoading()
            }
        }
    }
}
