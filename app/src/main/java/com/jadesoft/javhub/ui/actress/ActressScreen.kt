package com.jadesoft.javhub.ui.actress

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.actress.ActressScaffold


@SuppressLint("UnrememberedMutableState")
@Composable
fun ActressScreen(
    code: String,
    name: String,
    avatarUrl: String,
    censored: Boolean,
    navController: NavController,
    actressViewModel: ActressViewModel = hiltViewModel<ActressViewModel>()
) {
    val actressState = actressViewModel.actressState.collectAsState()
    val movies = actressState.value.movies
    val page = actressState.value.pagination.page
    val isLoadingMovie = actressState.value.isLoadingMovie
    val isChecking = actressState.value.isChecking
    val hasMore = actressState.value.pagination.hasMore
    val itemStyle = actressState.value.itemStyle
    val itemNum = actressState.value.itemNum
    val isBlurred = actressState.value.isBlurred
    val isFollowed = actressState.value.isFollowed

    val scrollState = rememberLazyGridState()
    val isPullDown by derivedStateOf {
        scrollState.firstVisibleItemScrollOffset > 20
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        actressViewModel.loadActress(censored, code)
        actressViewModel.loadMovies(censored, code)

        actressViewModel.snackbarEvent.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(scrollState, movies) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == movies.size - 1
                    && !isLoadingMovie
                    && hasMore) {
                    actressViewModel.loadMovies(censored, code)
                }
            }
    }

    ActressScaffold(
        name = name,
        movies = movies,
        isFollowed = isFollowed,
        itemStyle = itemStyle,
        itemNum = itemNum,
        isBlurred = isBlurred,
        page = page,
        isLoadingMovie = isLoadingMovie,
        isChecking = isChecking,
        isPullDown = isPullDown,
        snackBarState = snackbarHostState,
        scrollState = scrollState,
        navController = navController,
        addFollow = actressViewModel::onEvent,
        removeFollow = actressViewModel::onEvent
    )

}