package com.jadesoft.javhub.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.jadesoft.javhub.presentation.setting.SettingSubcategory
import com.jadesoft.javhub.presentation.setting.SettingTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralScreen(
    isPublic: Boolean,
    isStealth: Boolean,
    onTogglePublic: (SettingEvent.TogglePublicMode) -> Unit,
    onToggleStealth: (SettingEvent.ToggleStealthMode) -> Unit,
    navigateBack: () -> Unit = {}
) {

    val scrollState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )

    Scaffold(
        topBar = {
            SettingTopBar(
                title = "常规",
                scrollBehavior = scrollBehavior,
                navigateBack = navigateBack,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            item(1) {
                SettingSubcategory(
                    title = "偏好设置",
                    content = {
                        Column {
                            ListItem(
                                leadingContent = { Icon(Icons.Filled.ChildCare, contentDescription = "公共模式", tint = MaterialTheme.colorScheme.secondary) },
                                headlineContent = { Text("公共模式") },
                                supportingContent = { Text("在公共场合防止尴尬") },
                                trailingContent = { Switch(
                                    checked = isPublic,
                                    onCheckedChange = { onTogglePublic(SettingEvent.TogglePublicMode) }
                                ) },
                                modifier = Modifier.clickable {  }
                            )
                            ListItem(
                                leadingContent = { Icon(Icons.Filled.RemoveRedEye, contentDescription = "无痕模式", tint = MaterialTheme.colorScheme.secondary) },
                                headlineContent = { Text("无痕模式") },
                                supportingContent = { Text("暂停历史记录") },
                                trailingContent = { Switch(
                                    checked = isStealth,
                                    onCheckedChange = { onToggleStealth(SettingEvent.ToggleStealthMode) }
                                ) },
                                modifier = Modifier.clickable {  }
                            )
                        }
                    }
                )
            }
        }
    }
}