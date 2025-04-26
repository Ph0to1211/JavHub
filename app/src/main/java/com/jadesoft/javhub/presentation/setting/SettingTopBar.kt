package com.jadesoft.javhub.presentation.setting

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    navigateBack: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(title)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(0.dp),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
        ),
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "back")
            }
        },
        scrollBehavior = scrollBehavior
    )
}