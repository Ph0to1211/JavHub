package com.jadesoft.javhub.ui.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.search.SearchFilterBar
import com.jadesoft.javhub.presentation.search.SearchHistoryContent
import com.jadesoft.javhub.presentation.search.SearchResultContent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>()
) {
    val searchState = searchViewModel.searchState.collectAsState()

    val searchHistories = searchState.value.searchHistories
    val searchQuery = searchState.value.searchQuery
    val searchError = searchState.value.searchError
    val showResult = searchState.value.showResult
    val onlyShowMag = searchState.value.onlyShowMag
    val selectedIndex = searchState.value.selectedIndex
    val movies = searchState.value.items
    val actress = searchState.value.actress
    val page = searchState.value.pagination.page
    val isLoading = searchState.value.isLoading
    val hasMore = searchState.value.pagination.hasMore
    val itemStyle = searchState.value.itemStyle
    val itemNum = searchState.value.itemNum
    val isBlurred = searchState.value.isBlurred
    val scrollState = searchViewModel.scrollState

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        searchViewModel.onEvent(SearchEvent.GetSearchHistory)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                title = {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchViewModel.onEvent(SearchEvent.ValueChange(it))
                            if (searchError != null) {
                                searchViewModel.clearValidationError()
                            }
                        },
                        isError = searchError != null,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        shape = RoundedCornerShape(60.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            errorBorderColor = MaterialTheme.colorScheme.error,
                            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                        ),
                        placeholder = {
                            if (searchError != null) {
                                Text(
                                    text = searchError,
                                    color = MaterialTheme.colorScheme.error
                                )
                            } else {
                                Text("请输入关键词")
                            }
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                searchViewModel.onEvent(SearchEvent.SubmitSearch(true))
                                focusManager.clearFocus()
                            }
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (searchError != null) {
                                Icon(
                                    Icons.Filled.Error,
                                    "错误",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            } else if (searchQuery != "") {
                                IconButton(
                                    onClick = {
                                        searchViewModel.onEvent(SearchEvent.ClearQuery)
                                        focusManager.clearFocus()
                                    }
                                ) {
                                    Icon(Icons.Default.Close, "清除内容")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                actions = {
                    Row {
                        AnimatedVisibility(
                            visible = showResult,
                            enter =
                            fadeIn(animationSpec = tween(300)) + expandHorizontally(animationSpec = tween(300)),
                            exit =
                            fadeOut(animationSpec = tween(300)) + shrinkHorizontally(animationSpec = tween(300))
                        ) {
                            IconButton(
                                onClick = { searchViewModel.onEvent(SearchEvent.ToggleMag) },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                if (onlyShowMag) {
                                    Icon(Icons.Default.Link, "已有磁链", tint = MaterialTheme.colorScheme.secondary)
                                } else {
                                    Icon(Icons.Default.LinkOff, "全部影片", tint = MaterialTheme.colorScheme.secondary)
                                }
                            }
                        }
                        IconButton(
                            onClick = {
                                searchViewModel.onEvent(SearchEvent.SubmitSearch(true))
                                focusManager.clearFocus()
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        ) {
                            Icon(Icons.Default.Search, "搜索", tint = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            if (showResult) {
                SearchFilterBar(
                    selectedIndex = selectedIndex,
                    modifierType = searchViewModel::onEvent
                )
                SearchResultContent(
                    movies = movies,
                    actress = actress,
                    selectedIndex = selectedIndex,
                    page = page,
                    isLoading = isLoading,
                    hasMore = hasMore,
                    itemStyle = itemStyle,
                    itemNum = itemNum,
                    isBlurred = isBlurred,
                    scrollState = scrollState,
                    getData = searchViewModel::onEvent,
                    navController = navController
                )
            } else {
                SearchHistoryContent(
                    searchHistories = searchHistories,
                    deleteSingleSearchHistory = searchViewModel::onEvent,
                    deleteSearchHistory = searchViewModel::onEvent,
                    onHistoryItemClicked = searchViewModel::onEvent
                )
            }
        }
    }
}