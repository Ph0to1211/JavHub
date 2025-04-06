package com.jadesoft.javhub.ui.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jadesoft.javhub.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val preferences: PreferencesManager
) : ViewModel(){

    var libraryListState by mutableStateOf(LazyListState())
        private set

    var historyListState by mutableStateOf(LazyListState())
        private set

    var categoryListState by mutableStateOf(LazyListState())
        private set

    var exploreListState by mutableStateOf(LazyGridState())
        private set

    var moreListState by mutableStateOf(LazyListState())
        private set

    var currentRoute by mutableStateOf("library")
        private set

    private val _showFilter = MutableStateFlow<Boolean>(false)
    val showFilter: StateFlow<Boolean> = _showFilter

    private val _showMenu = MutableStateFlow<Boolean>(false)
    val showMenu: StateFlow<Boolean> = _showMenu

    private val _exploreType = MutableStateFlow<Boolean>(preferences.exploreType)
    val exploreType: StateFlow<Boolean> = _exploreType

    private val _refreshEvent = MutableStateFlow<Unit>(Unit)
    val refreshEvent: SharedFlow<Unit> = _refreshEvent.asSharedFlow()

    val currentScrollState by derivedStateOf {
        when (currentRoute) {
            "library" -> libraryListState
            "history" -> historyListState
            "category" -> categoryListState
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
                "category" -> categoryListState.scrollToItem(0)
                "explore" -> exploreListState.scrollToItem(0)
                "more" -> moreListState.scrollToItem(0)
            }
        }
    }

    fun toggleFilter() {
        _showFilter.value = !_showFilter.value
    }

    fun toggleShowMenu() {
        _showMenu.value = !_showMenu.value
    }

//    fun toggleExploreType(type: Boolean) {
//        _exploreType.value = type
//        preferences.exploreType = type
//    }

    fun toggleExploreType(enabled: Boolean) {
        _exploreType.value = enabled
        viewModelScope.launch {
            _refreshEvent.emit(Unit)
        }
    }

}