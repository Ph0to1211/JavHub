package com.jadesoft.javhub.ui.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.library.LibraryContent

@Composable
fun LibraryScreen(
    navController: NavController,
    libraryViewModel: LibraryViewModel = hiltViewModel<LibraryViewModel>()
) {

    val libraryState = libraryViewModel.libraryState.collectAsState()
    val movies = libraryState.value.movies

    val itemStyle = libraryState.value.itemStyle
    val itemNum = libraryState.value.itemNum
    val isBlurred = libraryState.value.isBlurred
    val tags = libraryState.value.tags

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tags.size })
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        libraryViewModel.onEvent(LibraryEvent.LoadItems)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LibraryContent(
            movies = movies,
            scrollState = scrollState,
            navController = navController,
            itemStyle = itemStyle,
            itemNum = itemNum,
            isBlurred = isBlurred,
            tags = tags,
            pagerState = pagerState,
            coroutineScope = coroutineScope
        )
    }
}