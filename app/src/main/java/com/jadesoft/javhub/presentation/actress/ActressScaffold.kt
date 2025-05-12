package com.jadesoft.javhub.presentation.actress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.presentation.common.MovieList
import com.jadesoft.javhub.presentation.common.MyTopAppBar
import com.jadesoft.javhub.ui.actress.ActressEvent

@Composable
fun ActressScaffold(
    name: String,
    movies: List<Movie>,
    isFollowed: Boolean,
    itemStyle: Int,
    itemNum: Int,
    isBlurred: Boolean,
    page: Int,
    isLoadingMovie: Boolean,
    isChecking: Boolean,
    isPullDown: Boolean,
    snackBarState: SnackbarHostState,
    scrollState: LazyGridState,
    navController: NavController,
    addFollow: (ActressEvent.OnAddFollow) -> Unit,
    removeFollow: (ActressEvent.OnRemoveFollow) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarState) },
        topBar = { MyTopAppBar(
            navController = navController,
            isPullDown = isPullDown,
            showHome = false,
            title = name,
            actions = {
                if (!isChecking) {
                    if (!isFollowed) {
                        IconButton(
                            onClick = { addFollow(ActressEvent.OnAddFollow) }
                        ) {
                            Icon(Icons.Outlined.FavoriteBorder, "关注")
                        }
                    } else {
                        IconButton(
                            onClick = { removeFollow(ActressEvent.OnRemoveFollow) }
                        ) {
                            Icon(Icons.Default.Favorite, "取消关注")
                        }
                    }
                } else {
                    CircularLoading()
                }
            }
        ) }
    ) { innerPadding ->
        Column( Modifier.padding(innerPadding).fillMaxSize() ) {
            if (page != 1) {
                MovieList(
                    movies = movies,
                    isLoading =  isLoadingMovie,
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