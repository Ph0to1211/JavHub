package com.jadesoft.javhub.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowSize
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jadesoft.javhub.presentation.home.HomeDrawer
import com.jadesoft.javhub.ui.explore.ExploreScreen
import com.jadesoft.javhub.ui.history.HistoryScreen
import com.jadesoft.javhub.ui.library.LibraryScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
    val isDrawerOpen = homeViewModel.isDrawerOpen.collectAsState()

    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route ?: "library"

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(currentRoute) {
        homeViewModel.updateCurrentRoute(currentRoute)
    }

    LaunchedEffect(isDrawerOpen.value) {
        if (drawerState.isClosed && initialized) {
            drawerState.open()
        } else {
            initialized = true
        }
    }

    val bottomBarRoute = listOf(
        BottomBarRoute("收藏", "library", Icons.Filled.Bookmarks, Icons.Outlined.Bookmarks),
        BottomBarRoute("发现", "explore", Icons.Filled.Explore, Icons.Outlined.Explore),
        BottomBarRoute("历史", "history", Icons.Filled.History, Icons.Outlined.History),
    )

    val windowSize = with(LocalDensity.current) {
        currentWindowSize().toSize().toDpSize()
    }
    val layoutType = if (windowSize.width >= 1200.dp) {
        NavigationSuiteType.NavigationDrawer
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
            currentWindowAdaptiveInfo()
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                HomeDrawer(
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                    navController = navController
                )
            }
        },
    ) {
        NavigationSuiteScaffold(
            layoutType = layoutType,
            navigationSuiteItems = {
                bottomBarRoute.forEachIndexed { index, item ->
                    item(
                        selected = currentRoute == item.route,
                        onClick = {
                            homeNavController.navigate(item.route) {
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                },
                                contentDescription = item.name
                            )
                        },
                        label = { Text(item.name) }
                    )
                }
            }
        ) {
            NavHost(
                navController = homeNavController,
                startDestination = "library",
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                composable("library") { LibraryScreen(navController) }
                composable("history") { HistoryScreen(navController) }
                composable("explore") { ExploreScreen(navController) }
            }
        }
    }
}

data class BottomBarRoute(
    val name: String, val route: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
)
