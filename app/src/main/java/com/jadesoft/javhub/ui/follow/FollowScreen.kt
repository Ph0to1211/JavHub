package com.jadesoft.javhub.ui.follow

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.common.MyTopAppBar
import com.jadesoft.javhub.presentation.common.NoDataTip
import com.jadesoft.javhub.presentation.follow.FollowDialog
import com.jadesoft.javhub.presentation.follow.FollowList

@SuppressLint("UnrememberedMutableState")
@Composable
fun FollowScreen(
    navController: NavController,
    followViewModel: FollowViewModel = hiltViewModel<FollowViewModel>()
) {

    val followState = followViewModel.followState.collectAsState()
    val actress = followState.value.followings
    val isShowDialog = followState.value.isShowDialog

    val scrollState = rememberLazyListState()
    val isPullDown by derivedStateOf {
        scrollState.firstVisibleItemScrollOffset > 20
    }

    LaunchedEffect(Unit) {
        followViewModel.loadItems()
    }

    Scaffold(
        topBar = { MyTopAppBar(
            navController = navController,
            isPullDown = isPullDown,
            showHome = false,
            title = "关注",
            actions = {
                IconButton(
                    onClick = { navController.navigate("search") }
                ) {
                    Icons.Outlined.PersonAdd
                }
            }
        ) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        if (actress.isNotEmpty()) {
            FollowList(
                actress = actress,
                scrollState = scrollState,
                innerPadding = innerPadding,
                navController = navController,
                onToggleShowDialog = followViewModel::onEvent
            )
        } else {
            NoDataTip(
                tip = "暂无关注，去发现页看看吧",
                canRefresh = false
            )
        }

        if (isShowDialog) {
            FollowDialog(
                onDismiss = followViewModel::onEvent,
                onConfirm = followViewModel::onEvent
            )
        }
    }

}