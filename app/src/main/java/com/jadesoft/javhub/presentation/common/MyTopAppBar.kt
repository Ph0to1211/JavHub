package com.jadesoft.javhub.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.navigation.Home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavController,
    isPullDown: Boolean,
    title: String,
    showHome: Boolean,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = if (isPullDown) MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp)
            else MaterialTheme.colorScheme.surface.copy(0f)
        ),
        title = {
            Text(text = title)
//            AnimatedVisibility(visible = isPullDown) {
//                Text(text = title)
//            }
        },
        navigationIcon = {
            Row {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                }
                if (showHome) {
                    IconButton(
                        onClick = {
                            navController.navigate(Home) {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Icon(Icons.Default.Home, "回到首页")
                    }
                }
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        }
    )
}