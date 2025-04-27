package com.jadesoft.javhub.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jadesoft.javhub.ui.explore.ExploreScreen
import com.jadesoft.javhub.ui.history.HistoryScreen
import com.jadesoft.javhub.ui.library.LibraryScreen
import com.jadesoft.javhub.ui.more.MoreScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel<HomeViewModel>(),
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route ?: "library"

    LaunchedEffect(currentRoute) {
        homeViewModel.updateCurrentRoute(currentRoute)
    }

    val bottomBarRoute = listOf(
        BottomBarRoute("书架", "library", Icons.Filled.Bookmarks, Icons.Outlined.Bookmarks),
        BottomBarRoute("历史", "history", Icons.Filled.History, Icons.Outlined.History),
        BottomBarRoute("发现", "explore", Icons.Filled.Explore, Icons.Outlined.Explore),
        BottomBarRoute("更多", "more", Icons.Filled.MoreHoriz, Icons.Outlined.MoreHoriz)
    )

    Scaffold(
        topBar = { },
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
//                            if (route.route != "library") {
//                                homeNavController.navigate(route.route) {
//                                    popUpTo(homeNavController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    launchSingleTop = true
//                                    restoreState = true
//                                }
//                            } else {
//                                homeNavController.navigate(route.route)
//                            }
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
        content = {
            NavHost(
                homeNavController,
                startDestination = "library",
                enterTransition = { fadeIn(animationSpec = tween(durationMillis = 300)) },
                exitTransition = { fadeOut(animationSpec = tween(durationMillis = 300)) },
            ) {
                composable("library") { LibraryScreen(navController) }
                composable("history") { HistoryScreen(navController) }
                composable("explore") { ExploreScreen(navController) }
                composable("more") { MoreScreen(navController) }
            }
        }
    )
}

data class BottomBarRoute(
    val name: String, val route: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector
)
