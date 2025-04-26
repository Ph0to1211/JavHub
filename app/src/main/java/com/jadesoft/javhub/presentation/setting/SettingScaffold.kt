package com.jadesoft.javhub.presentation.setting

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DisplaySettings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.jadesoft.javhub.ui.setting.SettingRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScaffold(
    scrollState: ScrollState,
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavController,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SettingTopBar(
                title = "设置",
                scrollBehavior = scrollBehavior,
                navigateBack = navigateBack,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            SettingItem(
                icon = Icons.Outlined.DisplaySettings,
                title = "常规",
                description = "偏好·显示",
                onClick = { navController.navigate(SettingRoutes.General) }
            )

            SettingItem(
                icon = Icons.Outlined.Palette,
                title = "外观",
                description = "主题·颜色",
                onClick = { navController.navigate("appearance") }
            )

            SettingItem(
                icon = Icons.Outlined.Storage,
                title = "数据与存储",
                description = "存储空间",
                onClick = { navController.navigate("storage") }
            )

            SettingItem(
                icon = Icons.Outlined.Lock,
                title = "隐私",
                description = "应用锁·隐私界面",
                onClick = { navController.navigate("privacy") }
            )

            SettingItem(
                icon = Icons.Outlined.Info,
                title = "关于",
                description = "JavHub 0.99.0",
                onClick = { navController.navigate("about") }
            )
        }
    }
}