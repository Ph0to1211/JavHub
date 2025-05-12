package com.jadesoft.javhub.presentation.follow

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.ui.follow.FollowEvent

@Composable
fun FollowList(
    actress: List<Actress>,
    scrollState: LazyListState,
    innerPadding: PaddingValues,
    navController: NavController,
    onToggleShowDialog: (FollowEvent.ToggleShowDialog) -> Unit,

    ) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier.padding(innerPadding)
    ) {
        items(actress) { item ->
            FollowListItem(
                actress = item,
                onToggleShowDialog = onToggleShowDialog,
                navController = navController
            )
        }
    }
}