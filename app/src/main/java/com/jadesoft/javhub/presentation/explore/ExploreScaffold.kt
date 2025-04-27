package com.jadesoft.javhub.presentation.explore

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.ui.explore.ExploreEvent
import com.jadesoft.javhub.ui.explore.ExploreItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ExploreScaffold(
    // 导航相关
    navController: NavController,

    // 状态控制参数（按操作流程排序）
    showUncensored: Boolean,
    isFiltered: Boolean,
    filterName: String,
    exploreType: Boolean,
    showMenu: Boolean,
    page: Int,
    isLoadingMovie: Boolean,
    isLoadingGenre: Boolean,
    isBlurred: Boolean,
    itemNum: Int,
    showFilter: Boolean,
    onlyShowMag: Boolean,

    // 数据展示参数
    items: List<ExploreItem>,
    genres: Map<String, List<Genre>>,
    filterOptions: List<Genre>,

    // UI状态参数
    scrollState: LazyGridState,
    sheetState: SheetState,
    itemStyle: Int,

    // 回调事件（按操作频率排序）
    toggleFilter: (ExploreEvent.ToggleFilter) -> Unit,
    toggleGenre: (ExploreEvent.ToggleGenre) -> Unit,
    resetFilterOption: (ExploreEvent.ResetFilter) -> Unit,
    toggleItemStyle: (ExploreEvent.ToggleItemStyle) -> Unit,
    toggleItemNum: (ExploreEvent.ToggleItemNum) -> Unit,
    submitFilterOption: (ExploreEvent.SubmitFilter) -> Unit,
    toggleMag: (ExploreEvent.ToggleMag) -> Unit,
    editFilterOptions: (ExploreEvent.EditFilterOption) -> Unit,
    onToggleMenu: (ExploreEvent.ToggleMenu) -> Unit,
    onToggleExploreType: (ExploreEvent.ToggleExploreType) -> Unit,
    onRefreshDatta: (ExploreEvent.RefreshData) -> Unit,
    onToggleFilter: (ExploreEvent.ToggleFilter) -> Unit,
    navigateToSearch: () -> Unit
) {
    Scaffold(
        topBar = {
            ExploreTopBar(
                showMenu = showMenu,
                exploreType = exploreType,
                onToggleMenu = onToggleMenu,
                onToggleExploreType = onToggleExploreType,
                onToggleFilter = onToggleFilter,
                navigateToSearch = navigateToSearch
            )
        },
        bottomBar = {
            NavigationBar {  }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                ExploreFilterBar(
                    showUncensored = showUncensored,
                    isFiltered = isFiltered,
                    filterName = filterName,
                    exploreType = exploreType,
                    toggleFilter = toggleFilter,
                    toggleGenre = toggleGenre,
                    resetFilterOption = resetFilterOption
                )

                if (page == 1) {
                    CircularLoading()
                } else {
                    when {
                        exploreType -> {
                            ExploreMovieContent(
                                movies = items.map { (it as ExploreItem.MovieItem).data },
                                isLoading = isLoadingMovie,
                                scrollState = scrollState,
                                navController = navController,
                                itemStyle = itemStyle,
                                isBlurred = isBlurred,
                                itemNum = itemNum,
                                refreshData = { onRefreshDatta(ExploreEvent.RefreshData) }
                            )
                        }
                        else -> {
                            ExploreActressContent(
                                actresses = items.map { (it as ExploreItem.ActressItem).data },
                                isLoading = isLoadingMovie,
                                censoredType = showUncensored,
                                scrollState = scrollState,
                                navController = navController,
                                refreshData = { onRefreshDatta(ExploreEvent.RefreshData) }
                            )
                        }
                    }

                    if (showFilter) {
                        ExploreBottomSheet(
                            sheetState = sheetState,
                            onDismissRequest = {
                                toggleFilter(ExploreEvent.ToggleFilter)
                            },
                            isLoading = isLoadingGenre,
                            genres = genres,
                            onlyShowMag = onlyShowMag,
                            showUncensored = showUncensored,
                            itemStyle = itemStyle,
                            itemNum = itemNum,
                            filterOptions = filterOptions,
                            resetFilterOption = resetFilterOption,
                            submitFilterOption = submitFilterOption,
                            toggleGenre = toggleGenre,
                            toggleMag = toggleMag,
                            editFilterOptions = editFilterOptions,
                            toggleItemStyle = toggleItemStyle,
                            toggleItemNum = toggleItemNum
                        )
                    }
                }
            }
        }
    )
}