package com.jadesoft.javhub.ui.genre

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.presentation.common.MovieList
import com.jadesoft.javhub.presentation.common.MyTopAppBar

@SuppressLint("UnrememberedMutableState")
@Composable
fun GenreScreen(
    code: String,
    name: String,
    type: Boolean,
    navController: NavController,
    genreViewModel: GenreViewModel = hiltViewModel<GenreViewModel>()
) {
    val scrollState = rememberLazyGridState()
    val isPullDown by derivedStateOf {
        scrollState.firstVisibleItemScrollOffset > 20
    }

    val movies = genreViewModel.items.collectAsState()
    val page = genreViewModel.page.collectAsState()
    val isLoading = genreViewModel.isLoading.collectAsState()
    val hasMore = genreViewModel.hasMore.collectAsState()
    val itemStyle = genreViewModel.itemStyle.collectAsState()

    LaunchedEffect(Unit) {
        genreViewModel.getData(code, type)
    }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == movies.value.size - 1
                    && !isLoading.value
                    && hasMore.value) {
                    genreViewModel.getData(code, type)
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
            if (page.value != 1) {
                MovieList(
                    movies = movies.value,
                    isLoading =  isLoading.value,
                    navController = navController,
                    scrollState = scrollState,
                    itemStyle = itemStyle.value,
                    itemNum = 3,
                    isBlurred = false
                )
            } else {
                CircularLoading()
            }
        }
    }
}