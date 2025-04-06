package com.jadesoft.javhub.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var libraryListState by mutableStateOf(LazyListState())
        private set

    var historyListState by mutableStateOf(LazyListState())
        private set

    var exploreListState by mutableStateOf(LazyGridState())
        private set

    var moreListState by mutableStateOf(LazyListState())
        private set

    var currentRoute by mutableStateOf("library")
        private set

    val currentScrollState by derivedStateOf {
        when (currentRoute) {
            "library" -> libraryListState
            "history" -> historyListState
            "explore" -> exploreListState
            "more" -> moreListState
            else -> null
        }
    }

    val isPullDown by derivedStateOf {
        when (currentScrollState) {
            is LazyGridState -> ((currentScrollState as LazyGridState).firstVisibleItemScrollOffset ?: 0) > 20
            is LazyListState -> ((currentScrollState as LazyListState).firstVisibleItemScrollOffset ?: 0) > 20
            else -> false
        }
    }

    fun updateCurrentRoute(route: String) {
        currentRoute = route
    }


    fun scrollToTop() {
        viewModelScope.launch {
            when (currentRoute) {
                "library" -> libraryListState.scrollToItem(0)
                "history" -> historyListState.scrollToItem(0)
                "explore" -> exploreListState.scrollToItem(0)
                "more" -> moreListState.scrollToItem(0)
            }
        }
    }

}