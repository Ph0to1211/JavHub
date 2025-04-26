package com.jadesoft.javhub.ui.more

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun MoreScreen(
    navController: NavController,
    moreViewModel: MoreViewModel = hiltViewModel<MoreViewModel>()
) {

    val moreState = moreViewModel.moreState.collectAsState()

    val isPublic = moreState.value.isPublic
    val isStealth = moreState.value.isStealth

    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "ヾ(≧▽≦*)o",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(60.dp).align(Alignment.CenterHorizontally),
            )
            HorizontalDivider()
            ListItem(
                leadingContent = { Icon(Icons.Filled.ChildCare, contentDescription = "公共模式", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("公共模式") },
                supportingContent = { Text("在公共场合防止尴尬") },
                trailingContent = { Switch(
                    checked = isPublic,
                    onCheckedChange = {
                        moreViewModel.onEvent(MoreEvent.TogglePublicMode)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("重启后生效")
                        }
                    }
                ) },
                modifier = Modifier.clickable {  }
            )
            ListItem(
                leadingContent = { Icon(Icons.Filled.RemoveRedEye, contentDescription = "无痕模式", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("无痕模式") },
                supportingContent = { Text("暂停历史记录") },
                trailingContent = { Switch(
                    checked = isStealth,
                    onCheckedChange = {
                        moreViewModel.onEvent(MoreEvent.ToggleStealthMode)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("重启后生效")
                        }
                    }
                ) },
                modifier = Modifier.clickable {  }
            )
            HorizontalDivider()
            ListItem(
                leadingContent = { Icon(Icons.Filled.BookmarkBorder, contentDescription = "标签", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("标签") },
                modifier = Modifier.clickable {
                    navController.navigate("tag")
                }
            )
            ListItem(
                leadingContent = { Icon(Icons.Filled.BarChart, contentDescription = "统计", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("统计") },
                modifier = Modifier.clickable {
                    Toast.makeText(context, "尚在开发中", Toast.LENGTH_SHORT).show()
                }
            )
            ListItem(
                leadingContent = { Icon(Icons.Filled.Storage, contentDescription = "数据与存储", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("数据与存储") },
                modifier = Modifier.clickable {
                    Toast.makeText(context, "尚在开发中", Toast.LENGTH_SHORT).show()
                }
            )
            HorizontalDivider()
            ListItem(
                leadingContent = { Icon(Icons.Filled.Settings, contentDescription = "设置", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("设置") },
                modifier = Modifier.clickable {
                    navController.navigate("setting")
                }
            )
            ListItem(
                leadingContent = { Icon(Icons.Filled.Info, contentDescription = "关于", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("关于") },
                modifier = Modifier.clickable {
                    Toast.makeText(context, "尚在开发中", Toast.LENGTH_SHORT).show()
                }
            )
            ListItem(
                leadingContent = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "帮助", tint = MaterialTheme.colorScheme.secondary) },
                headlineContent = { Text("帮助") },
                modifier = Modifier.clickable {
                    Toast.makeText(context, "尚在开发中", Toast.LENGTH_SHORT).show()
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}