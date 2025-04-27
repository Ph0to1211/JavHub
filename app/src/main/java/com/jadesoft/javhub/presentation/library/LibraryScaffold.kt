package com.jadesoft.javhub.presentation.library

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Movie
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LibraryScaffold(
    movies: Map<String, List<Movie>>,
    scrollState: LazyGridState,
    navController: NavController,
    itemStyle: Int,
    itemNum: Int,
    isBlurred: Boolean,
    tags: List<String>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("书架") }
            )
        },
        bottomBar = {
            NavigationBar {  }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                LibraryContent(
                    movies = movies,
                    scrollState = scrollState,
                    navController = navController,
                    itemStyle = itemStyle,
                    itemNum = itemNum,
                    isBlurred = isBlurred,
                    tags = tags,
                    pagerState = pagerState,
                    coroutineScope = coroutineScope
                )
            }
        }
    )
}