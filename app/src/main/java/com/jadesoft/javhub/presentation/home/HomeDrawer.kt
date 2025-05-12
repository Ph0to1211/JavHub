package com.jadesoft.javhub.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeDrawer(
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(36.dp))
        Text("ヾ(≧▽≦*)o", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        ListItem(
            headlineContent = { Text("快捷入口", style = MaterialTheme.typography.titleSmall) },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent
            )
        )
        NavigationDrawerItem(
            label = { Text("关注") },
            selected = false,
            icon = { Icon(Icons.Filled.Group, contentDescription = "关注", tint = MaterialTheme.colorScheme.secondary) },
            onClick = { navController.navigate("follow") }
        )
        NavigationDrawerItem(
            label = { Text("标签") },
            selected = false,
            icon = { Icon(Icons.Filled.BookmarkBorder, contentDescription = "标签", tint = MaterialTheme.colorScheme.secondary) },
            onClick = { navController.navigate("tag") }
        )
        NavigationDrawerItem(
            label = { Text("统计") },
            selected = false,
            icon = { Icon(Icons.Filled.BarChart, contentDescription = "统计", tint = MaterialTheme.colorScheme.secondary) },
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("尚在开发中")
                }
            },
        )
        NavigationDrawerItem(
            label = { Text("数据与存储") },
            selected = false,
            icon = { Icon(Icons.Filled.Storage, contentDescription = "数据与存储", tint = MaterialTheme.colorScheme.secondary) },
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("尚在开发中")
                }
            },
        )

        ListItem(
            headlineContent = { Text("设置 & 其他", style = MaterialTheme.typography.titleSmall) },
            colors = ListItemDefaults.colors(
                headlineColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent
            )
        )
        NavigationDrawerItem(
            label = { Text("设置") },
            selected = false,
            icon = { Icon(Icons.Filled.Settings, contentDescription = "设置", tint = MaterialTheme.colorScheme.secondary) },
            onClick = { navController.navigate("setting") }
        )
        NavigationDrawerItem(
            label = { Text("关于") },
            selected = false,
            icon = { Icon(Icons.Filled.Info, contentDescription = "关于", tint = MaterialTheme.colorScheme.secondary) },
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("尚在开发中")
                }
            },
        )
        NavigationDrawerItem(
            label = { Text("帮助") },
            selected = false,
            icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "帮助", tint = MaterialTheme.colorScheme.secondary) },
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("尚在开发中")
                }
            },
        )
    }
}