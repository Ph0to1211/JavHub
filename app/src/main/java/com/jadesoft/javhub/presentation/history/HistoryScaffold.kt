package com.jadesoft.javhub.presentation.history

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.History
import com.jadesoft.javhub.presentation.common.NoDataTip
import com.jadesoft.javhub.ui.history.HistoryEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HistoryScaffold(
    histories: List<History>,
    navController: NavController,
    isBlurred: Boolean,
    scrollState: LazyListState,
    isScrolled: Boolean,
    showDialog: Boolean,
    onDelete: (HistoryEvent.DeleteHistory) -> Unit,
    onDeleteAll: (HistoryEvent.DeleteAllHistory) -> Unit,
    onToggleShowDialog: (HistoryEvent.ToggleShowDialog) -> Unit
) {
    Scaffold(
        topBar = {
            HistoryTopBar(
                hasHistories = histories.isNotEmpty(),
                isScrolled = isScrolled,
                onToggleShowDialog = onToggleShowDialog
            )
        },
        bottomBar = {
            NavigationBar {  }
        },
        content = { innerPadding ->
            if (histories.isNotEmpty()) {
                HistoryContent(
                    histories = histories,
                    navController = navController,
                    isBlurred = isBlurred,
                    showDialog = showDialog,
                    scrollState = scrollState,
                    innerPadding = innerPadding,
                    onDelete = onDelete,
                    onDeleteAll = onDeleteAll,
                    onToggleShowDialog = onToggleShowDialog
                )
            } else {
                NoDataTip(
                    tip = "暂无历史记录，去发现页看看吧",
                    canRefresh = false
                )
            }
        }
    )
}