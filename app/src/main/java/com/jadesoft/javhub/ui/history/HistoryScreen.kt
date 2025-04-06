package com.jadesoft.javhub.ui.history


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.common.NoDataTip
import com.jadesoft.javhub.presentation.history.HistoryContent

@Composable
fun HistoryScreen(
    navController: NavController,
    historyViewModel: HistoryViewModel = hiltViewModel<HistoryViewModel>()
) {

    val historyState = historyViewModel.historyState.collectAsState()
    val histories = historyState.value.histories
    val isBlurred = historyState.value.isBlurred

    LaunchedEffect(Unit) {
        historyViewModel.onEvent(HistoryEvent.GetItems)
    }

    if (histories.isNotEmpty()) {
        HistoryContent(
            histories = histories,
            navController = navController,
            isBlurred = isBlurred,
            onDelete = historyViewModel::onEvent
        )
    } else {
        NoDataTip(
            tip = "暂无历史记录，去发现页看看吧",
            canRefresh = false
        )
    }

}