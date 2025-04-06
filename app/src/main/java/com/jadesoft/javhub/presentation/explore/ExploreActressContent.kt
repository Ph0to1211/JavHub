package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.navigation.MovieRoute
import com.jadesoft.javhub.navigation.TypedMovieRoute
import com.jadesoft.javhub.presentation.common.ActressAvatar
import com.jadesoft.javhub.presentation.common.MovieCard
import com.jadesoft.javhub.presentation.common.MovieListItem
import com.jadesoft.javhub.presentation.common.NoDataTip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreActressContent(
    actresses: List<Actress>,
    isLoading: Boolean,
    censoredType: Boolean,
    itemStyle: Int,
    scrollState: LazyGridState,
    navController: NavController,
    refreshData: () -> Unit
) {
    val state = rememberPullToRefreshState()

    val isList = remember(itemStyle) {
        when (itemStyle) {
            3 -> true
            else -> false
        }
    }

    val itemCount = remember(itemStyle) {
        when (itemStyle) {
            3 -> 1
            else -> itemStyle + 1
        }
    }

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
        if (actresses.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(5.dp),
                state = scrollState
            ) {
                itemsIndexed(actresses) { index, actress ->
                    key(index) {
                        ActressAvatar(
                            actress = actress,
                            size = 120.dp,
                            padding = 8.dp,
                            censoredType = censoredType,
                            onClick = { code, name, censoredType, listType ->
                            navController.navigate(
                                route = TypedMovieRoute(
                                    code = code,
                                    name = name,
                                    actressCode = actress.code,
                                    censoredType = censoredType,
                                    listType = listType
                                )
                            )
                        })
                    }
                }

                if (isLoading) {
                    item(span = { GridItemSpan(3) }) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                    }
                }
            }
        } else {
            NoDataTip("无结果", false)
        }
    }
}