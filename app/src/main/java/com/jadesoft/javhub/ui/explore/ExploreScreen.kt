package com.jadesoft.javhub.ui.explore

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.explore.ExploreScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    exploreViewModel: ExploreViewModel = hiltViewModel<ExploreViewModel>()
) {
    val scrollState = exploreViewModel.exploreScrollState

    val exploreState = exploreViewModel.exploreState.collectAsStateWithLifecycle()
    val items = exploreState.value.items
    val isLoadingMovie = exploreState.value.isLoadingItem
    val page = exploreState.value.pagination.page
    val hasMore = exploreState.value.pagination.hasMore
    val genres = exploreState.value.genres
    val isLoadingGenre = exploreState.value.isLoadingGenre
    val isFiltered = exploreState.value.isFiltered
    val itemStyle = exploreState.value.itemStyle
    val isBlurred = exploreState.value.isBlurred
    val showMenu = exploreState.value.showMenu
    val itemNum = exploreState.value.itemNum
    val onlyShowMag = exploreState.value.onlyShowMag
    val showUncensored = exploreState.value.showUncensored
    val filterOptions = exploreState.value.filterOptions
    val filterName = exploreState.value.filterName
    val exploreType = exploreState.value.exploreType
    val showFilter = exploreState.value.showFilter

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    LaunchedEffect(Unit) {
        exploreViewModel.onEvent(ExploreEvent.LoadItems)
        exploreViewModel.onEvent(ExploreEvent.LoadGenres)
    }

    LaunchedEffect(scrollState, items) {
        snapshotFlow { scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == items.size - 1
                    && !isLoadingMovie
                    && hasMore) {
                    exploreViewModel.onEvent(ExploreEvent.LoadItems)
                }
            }
    }

    ExploreScaffold(
        navController = navController,
        showUncensored = showUncensored,
        isFiltered = isFiltered,
        filterName = filterName,
        exploreType = exploreType,
        showMenu = showMenu,
        page = page,
        isLoadingMovie = isLoadingMovie,
        isLoadingGenre = isLoadingGenre,
        isBlurred = isBlurred,
        itemNum = itemNum,
        showFilter = showFilter,
        onlyShowMag = onlyShowMag,
        items = items,
        genres = genres,
        filterOptions = filterOptions,
        scrollState = scrollState,
        sheetState = sheetState,
        itemStyle = itemStyle,
        toggleFilter = exploreViewModel::onEvent,
        toggleGenre = exploreViewModel::onEvent,
        resetFilterOption = exploreViewModel::onEvent,
        toggleItemStyle = exploreViewModel::onEvent,
        toggleItemNum = exploreViewModel::onEvent,
        submitFilterOption = exploreViewModel::onEvent,
        toggleMag = exploreViewModel::onEvent,
        editFilterOptions = exploreViewModel::onEvent,
        onToggleMenu = exploreViewModel::onEvent,
        onToggleExploreType = exploreViewModel::onEvent,
        onRefreshDatta = exploreViewModel::onEvent,
        onToggleFilter = exploreViewModel::onEvent,
        onToggleDrawerOpen = exploreViewModel::onEvent,
        navigateToSearch = { navController.navigate("search") }
    )

}
