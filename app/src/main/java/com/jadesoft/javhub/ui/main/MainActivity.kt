package com.jadesoft.javhub.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import com.jadesoft.javhub.navigation.Home
import com.jadesoft.javhub.navigation.NavGraph
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