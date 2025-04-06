package com.jadesoft.javhub.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jadesoft.javhub.Application
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.navigation.Home
import com.jadesoft.javhub.navigation.NavGraph
import com.jadesoft.javhub.ui.explore.ExploreScreen
import com.jadesoft.javhub.ui.history.HistoryScreen
import com.jadesoft.javhub.ui.home.HomeScreen
import com.jadesoft.javhub.ui.library.LibraryScreen
import com.jadesoft.javhub.ui.more.MoreScreen
import com.jadesoft.javhub.ui.theme.JavHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JavHubTheme {
                SharedTransitionLayout {
                    NavGraph(
                        start = Home
                    )
                }
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        composable("home") {
            HomeScreen(navController)
        }
        composable("library") {
            LibraryScreen(navController)
        }
        composable("history") {
            HistoryScreen(navController)
        }
//        composable("explore") {
//            ExploreScreen(navController)
//        }
        composable("more") {
            MoreScreen(navController)
        }
    }

}