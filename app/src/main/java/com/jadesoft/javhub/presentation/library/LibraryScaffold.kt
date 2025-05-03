package com.jadesoft.javhub.presentation.library

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.ui.library.LibraryEvent
import kotlinx.coroutines.CoroutineScope

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibraryScaffold(
    movies: Map<String, List<Movie>>,
    selectedMovies: Set<Movie>,
    scrollState: LazyGridState,
    navController: NavController,
    itemStyle: Int,
    itemNum: Int,
    isBlurred: Boolean,
    tags: List<String>,
    showDialog: Boolean,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onSelect: (Movie) -> Unit = {},
    onUnSelect: (LibraryEvent.OnUnSelect) -> Unit,
    onSelectAll: (LibraryEvent.OnSelectAll) -> Unit,
    onToggleShowDialog: (LibraryEvent.OnToggleShowDialog) -> Unit,
    onReverseSelect: (LibraryEvent.OnReverseSelect) -> Unit,
    onUpdateCurrentMovies: (List<Movie>) -> Unit,
    onDialogDismiss: (LibraryEvent.OnToggleShowDialog) -> Unit,
    onDialogConfirm: (LibraryEvent.DeleteItems) -> Unit
) {
    Scaffold(
        topBar = {
            LibraryTopBar(
                selectedItemCount = selectedMovies.size,
                onUnSelect = onUnSelect,
                onSelectAll = onSelectAll,
                onReverseSelect = onReverseSelect,
                onToggleShowDialog = onToggleShowDialog
            )
        },
        bottomBar = {
            NavigationBar {  }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                LibraryContent(
                    movies = movies,
                    selectedMovies = selectedMovies,
                    scrollState = scrollState,
                    navController = navController,
                    itemStyle = itemStyle,
                    itemNum = itemNum,
                    isBlurred = isBlurred,
                    tags = tags,
                    pagerState = pagerState,
                    coroutineScope = coroutineScope,
                    onSelect = onSelect,
                    onUpdateCurrentMovies = onUpdateCurrentMovies
                )
            }

            if (showDialog) {
                LibraryDeleteDialog(
                    onDismiss = onDialogDismiss,
                    onConfirm = onDialogConfirm
                )
            }
        }
    )
}