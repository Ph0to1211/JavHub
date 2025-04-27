package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.presentation.common.ActressList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreActressContent(
    actresses: List<Actress>,
    isLoading: Boolean,
    censoredType: Boolean,
    scrollState: LazyGridState,
    navController: NavController,
    refreshData: () -> Unit
) {
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = refreshData,
        state = state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                state = state,
                isRefreshing = false,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        }
    ) {
        ActressList(
            actresses = actresses,
            isLoading = isLoading,
            censoredType = censoredType,
            scrollState = scrollState,
            navController = navController
        )
    }
}