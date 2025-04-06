package com.jadesoft.javhub.ui.home

import android.graphics.drawable.shapes.Shape
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.navigation.NavGraph
import com.jadesoft.javhub.ui.category.CategoryScreen
import com.jadesoft.javhub.ui.explore.ExploreEvent
import com.jadesoft.javhub.ui.explore.ExploreScreen
import com.jadesoft.javhub.ui.explore.ExploreViewModel
import com.jadesoft.javhub.ui.history.HistoryScreen
import com.jadesoft.javhub.ui.library.LibraryScreen
import com.jadesoft.javhub.ui.more.MoreScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
//    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
    shareViewModel: ShareViewModel = hiltViewModel<ShareViewModel>(),
    exploreViewModel: ExploreViewModel = hiltViewModel<ExploreViewModel>()
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route ?: "library"

    val exploreState = exploreViewModel.exploreState.collectAsState()
    val exploreType = exploreState.value.exploreType
    val showMenu = exploreState.value.showMenu

    LaunchedEffect(currentRoute) {
        shareViewModel.updateCurrentRoute(currentRoute)
    }

    val bottomBarRoute = listOf(
        BottomBarRoute("书架", "library", Icons.Filled.Bookmarks, Icons.Outlined.Bookmarks),
        BottomBarRoute("历史", "history", Icons.Filled.History, Icons.Outlined.History),
        BottomBarRoute("发现", "explore", Icons.Filled.Explore, Icons.Outlined.Explore),
        BottomBarRoute("更多", "more", Icons.Filled.MoreHoriz, Icons.Outlined.MoreHoriz)
    )
    val title = when (currentRoute) {
        "library" -> "书架"
        "history" -> "历史"
        "explore" -> "发现"
        "more" -> "更多"
        else -> "主页"
    }

    Scaffold(
        topBar = {
            TopAppBar(
//                colors = topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
//                        elevation = if (shareViewModel.isPullDown) 3.dp else 0.dp
//                    )
//                ),
                title = {
                    when (currentRoute) {
                        "explore" -> {
                            Box {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(60.dp))
                                        .clickable { exploreViewModel.onEvent(ExploreEvent.ToggleMenu) }
                                        .padding(start = 10.dp, end = 14.dp, top = 8.dp, bottom = 8.dp)
                                ) {
                                    if (showMenu) Icon(Icons.Default.ArrowDropDown, "")
                                        else Icon(Icons.Default.ArrowDropUp, "")
                                    if (exploreType) Text("影片") else Text("女优")
                                }
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { exploreViewModel.onEvent(ExploreEvent.ToggleMenu) }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("影片") },
                                        onClick = {
                                            exploreViewModel.onEvent(ExploreEvent.ToggleExploreType(true))
                                            exploreViewModel.onEvent(ExploreEvent.ToggleMenu)
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("女优") },
                                        onClick = {
                                            exploreViewModel.onEvent(ExploreEvent.ToggleExploreType(false))
                                            exploreViewModel.onEvent(ExploreEvent.ToggleMenu)
                                        }
                                    )
                                }
                            }
                        }
                        else -> Text(title)
                    }
                },
                actions = {
                    when (currentRoute) {
                        "explore" -> Row {
                            IconButton(
                                onClick = { navController.navigate("search") }
                            ) { Icon(Icons.Filled.Search, contentDescription = "Search movies") }
                            if (exploreType) IconButton(
                                onClick = { exploreViewModel.onEvent(ExploreEvent.ToggleFilter) }
                            ) { Icon(Icons.Filled.FilterList, contentDescription = "Filter movies") }
                            IconButton(
                                onClick = {  }
                            ) { Icon(Icons.Filled.MoreVert, contentDescription = "More options") }
                        }
                        else -> {}
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                bottomBarRoute.forEach { route ->
                    NavigationBarItem(
                        selected = currentDestination?.route == route.route,
                        onClick = {
                            homeNavController.navigate(route.route) {
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(route.name) },
                        icon = {
                            Icon(
                                if (currentDestination?.route == route.route) route.selectedIcon else route.unselectedIcon,
                                contentDescription = route.name
                            )
                        },
                    )
                }
            }
        },
        content = { innerPadding ->
            NavHost(
                homeNavController,
                startDestination = "library",
                Modifier.padding(innerPadding),
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = 300)) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
            ) {
                composable("library") { LibraryScreen(navController) }
                composable("history") { HistoryScreen(navController) }
                composable("explore") { ExploreScreen(navController, exploreViewModel, shareViewModel) }
                composable("more") { MoreScreen(navController) }
            }
//            NavGraph(
//                navController = homeNavController,
//                modifier = Modifier.padding(innerPadding),
//                start = "library",
//                enterTransition = fadeIn(animationSpec = tween(durationMillis = 300)),
//                exitTransition = fadeOut(animationSpec = tween(durationMillis = 300))
//            )
        },
        floatingActionButton = {
            if (shareViewModel.isPullDown) {
                FloatingActionButton(
                    onClick = { shareViewModel.scrollToTop() },
                ) {
                    Icon(Icons.Filled.ArrowUpward, "Up to top")
                }
            }
        }
    )
}

data class BottomBarRoute(
    val name: String, val route: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
)
