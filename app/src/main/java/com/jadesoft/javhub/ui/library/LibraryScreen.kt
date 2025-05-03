package com.jadesoft.javhub.ui.library

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.library.LibraryScaffold

@SuppressLint("UnrememberedMutableState")
@Composable
fun LibraryScreen(
    navController: NavController,
    libraryViewModel: LibraryViewModel = hiltViewModel<LibraryViewModel>()
) {

    val libraryState = libraryViewModel.libraryState.collectAsState()
    val movies = libraryState.value.movies
    val selectedMovies = libraryState.value.selectedMovies

    val itemStyle = libraryState.value.itemStyle
    val itemNum = libraryState.value.itemNum
    val isBlurred = libraryState.value.isBlurred
    val tags = libraryState.value.tags
    val showDialog = libraryState.value.showDialog

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tags.size })
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        libraryViewModel.onEvent(LibraryEvent.LoadItems)
    }

    LibraryScaffold(
        movies = movies,
        selectedMovies = selectedMovies,
        scrollState = scrollState,
        navController = navController,
        itemStyle = itemStyle,
        itemNum = itemNum,
        isBlurred = isBlurred,
        tags = tags,
        showDialog = showDialog,
        pagerState = pagerState,
        coroutineScope = coroutineScope,
        onSelect = { movie ->
            libraryViewModel.onEvent(LibraryEvent.OnSelect(movie))
        },
        onUnSelect = libraryViewModel::onEvent,
        onSelectAll = libraryViewModel::onEvent,
        onToggleShowDialog = libraryViewModel::onEvent,
        onReverseSelect = libraryViewModel::onEvent,
        onUpdateCurrentMovies = { currentMovies ->
            libraryViewModel.updateCurrentMovies(currentMovies)
        },
        onDialogDismiss = libraryViewModel::onEvent,
        onDialogConfirm = libraryViewModel::onEvent,
    )

}