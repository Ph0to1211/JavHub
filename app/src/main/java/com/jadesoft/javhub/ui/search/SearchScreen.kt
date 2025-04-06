package com.jadesoft.javhub.ui.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LinkOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.search.SearchFilterBar
import com.jadesoft.javhub.presentation.search.SearchResultContent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>()
) {
    val searchState = searchViewModel.searchState.collectAsState()

    val searchQuery = searchState.value.searchQuery
    val showResult = searchState.value.showResult
    val onlyShowMag = searchState.value.onlyShowMag
    val selectedIndex = searchState.value.selectedIndex
    val movies = searchState.value.items
    val page = searchState.value.pagination.page
    val isLoading = searchState.value.isLoading
    val hasMore = searchState.value.pagination.hasMore
    val itemStyle = searchState.value.itemStyle
    val itemNum = searchState.value.itemNum
    val isBlurred = searchState.value.isBlurred
    val scrollState = searchViewModel.scrollState

    val focusManager = LocalFocusManager.current

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
                        onValueChange = { searchViewModel.onEvent(SearchEvent.ValueChange(it)) },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        shape = RoundedCornerShape(60.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                        ),
                        placeholder = { Text("请输入关键词") },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                searchViewModel.onEvent(SearchEvent.SubmitSearch)
                                focusManager.clearFocus()
                            }
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (searchQuery != "") {
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
                                    Icon(Icons.Default.Link, "已有磁力", tint = MaterialTheme.colorScheme.secondary)
                                } else {
                                    Icon(Icons.Default.LinkOff, "查看全部", tint = MaterialTheme.colorScheme.secondary)
                                }
                            }
                        }
                        IconButton(
                            onClick = {
                                searchViewModel.onEvent(SearchEvent.SubmitSearch)
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("它需要做些什么...", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}