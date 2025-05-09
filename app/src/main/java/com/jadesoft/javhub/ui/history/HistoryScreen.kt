package com.jadesoft.javhub.ui.history


import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.history.HistoryScaffold

@SuppressLint("UnrememberedMutableState")
@Composable
fun HistoryScreen(
    navController: NavController,
    historyViewModel: HistoryViewModel = hiltViewModel<HistoryViewModel>()
) {

    val historyState = historyViewModel.historyState.collectAsState()
    val histories = historyState.value.histories
    val isBlurred = historyState.value.isBlurred
    val showDialog = historyState.value.showDialog

    val scrollState = rememberLazyListState()
    val isScrolled by derivedStateOf {
        scrollState.firstVisibleItemScrollOffset > 20
    }

    LaunchedEffect(Unit) {
        historyViewModel.onEvent(HistoryEvent.GetItems)
    }

    HistoryScaffold(
        histories = histories,
        navController = navController,
        isBlurred = isBlurred,
        scrollState = scrollState,
        isScrolled = isScrolled,
        showDialog = showDialog,
        onDelete = historyViewModel::onEvent,
        onDeleteAll = historyViewModel::onEvent,
        onToggleShowDialog = historyViewModel::onEvent,
        onToggleDrawerOpen = historyViewModel::onEvent
    )

}