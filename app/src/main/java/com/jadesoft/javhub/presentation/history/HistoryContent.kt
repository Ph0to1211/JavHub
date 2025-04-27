package com.jadesoft.javhub.presentation.history

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.History
import com.jadesoft.javhub.navigation.MovieRoute
import com.jadesoft.javhub.ui.history.HistoryEvent

@Composable
fun HistoryContent(
    histories: List<History>,
    navController: NavController,
    isBlurred: Boolean,
    showDialog: Boolean,
    scrollState: LazyListState,
    innerPadding: PaddingValues,
    onDelete: (HistoryEvent.DeleteHistory) -> Unit,
    onDeleteAll: (HistoryEvent.DeleteAllHistory) -> Unit,
    onToggleShowDialog: (HistoryEvent.ToggleShowDialog) -> Unit
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier.padding(innerPadding)
    ) {
        items(histories) { item ->
            when (item) {
                is History.DateItem -> HistoryDateItem(date = item.date)
                is History.MovieItem -> HistoryMovieItem(
                    movie = item.movie,
                    isBlurred = isBlurred,
                    onClick = {
                        navController.navigate(MovieRoute(item.movie.code, item.movie.cover, item.movie.title))
                    },
                    onDelete = {
                        onDelete(
                            HistoryEvent.DeleteHistory(item.movie.code)
                        )
                    }
                )
            }
        }
    }

    if (showDialog) {
        HistoryDialog(
            onDismiss = onToggleShowDialog,
            onConfirm = onDeleteAll
        )
    }
}