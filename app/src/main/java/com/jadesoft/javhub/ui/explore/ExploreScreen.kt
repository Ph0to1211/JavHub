package com.jadesoft.javhub.ui.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.presentation.explore.ExploreActressContent
import com.jadesoft.javhub.presentation.explore.ExploreBottomSheet
import com.jadesoft.javhub.presentation.explore.ExploreMovieContent
import com.jadesoft.javhub.presentation.explore.ExploreFilterBar
import com.jadesoft.javhub.ui.home.ShareViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    navController: NavController,
    exploreViewModel: ExploreViewModel,
    shareViewModel: ShareViewModel
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


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ExploreFilterBar(
            showUncensored = showUncensored,
            isFiltered = isFiltered,
            filterName = filterName,
            exploreType = exploreType,
            toggleFilter = exploreViewModel::onEvent,
            toggleGenre = exploreViewModel::onEvent,
            resetFilterOption = exploreViewModel::onEvent
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
                        refreshData = {
                            exploreViewModel.onEvent(ExploreEvent.RefreshData)
                        }
                    )
                }
                else -> {
                    ExploreActressContent(
                        actresses = items.map { (it as ExploreItem.ActressItem).data },
                        isLoading = isLoadingMovie,
                        censoredType = showUncensored,
                        itemStyle = itemStyle,
                        scrollState = scrollState,
                        navController = navController,
                        refreshData = {
                            exploreViewModel.onEvent(ExploreEvent.RefreshData)
                        }
                    )
                }
//                else -> CircularLoading()
            }

            if (showFilter) {
                ExploreBottomSheet(
//                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = {
                        exploreViewModel.onEvent(ExploreEvent.ToggleFilter)
                    },
                    isLoading = isLoadingGenre,
                    genres = genres,
                    onlyShowMag = onlyShowMag,
                    showUncensored = showUncensored,
                    itemStyle = itemStyle,
                    itemNum = itemNum,
                    filterOptions = filterOptions,
                    resetFilterOption = exploreViewModel::onEvent,
                    submitFilterOption = exploreViewModel::onEvent,
                    toggleGenre = exploreViewModel::onEvent,
                    toggleMag = exploreViewModel::onEvent,
                    editFilterOptions = exploreViewModel::onEvent,
                    toggleItemStyle = exploreViewModel::onEvent,
                    toggleItemNum = exploreViewModel::onEvent
                )
            }
        }
    }
}


//@Preview
//@Composable
//fun ItemPreview() {
//    BuildCard(Movie("FIG-017", "120%リアルガチ軟派伝説 vol.135【限定特典映像10分付き】", "https://image.mgstage.com/images/prestige/fig/017/pf_o1_fig-017.jpg", url = "https://www.javbus.com/FIG-017"))
//}