package com.jadesoft.javhub.presentation.follow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.Reorder
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.presentation.common.MyTopAppBar
import com.jadesoft.javhub.ui.follow.FollowEvent
import com.jadesoft.javhub.ui.follow.SortType

@Composable
fun FollowTopBar(
    isPullDown: Boolean,
    isMenuExpanded: Boolean,
    navController: NavController,
    toggleMenuExpanded: (FollowEvent.ToggleMenuExpanded) -> Unit,
    toggleSortType: (FollowEvent.ToggleSortType) -> Unit
) {
    MyTopAppBar(
        navController = navController,
        isPullDown = isPullDown,
        showHome = false,
        title = "关注",
        actions = {
            Box {
                IconButton(
                    onClick = { toggleMenuExpanded(FollowEvent.ToggleMenuExpanded) }
                ) {
                    Icon(Icons.Outlined.Reorder, "order")
                }

                // 下拉菜单
                DropdownMenu(
                    expanded = isMenuExpanded,
                    onDismissRequest = { toggleMenuExpanded(FollowEvent.ToggleMenuExpanded) },
                    modifier = Modifier.width(120.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text("按时间降序") },
                        onClick = {
                            toggleSortType(FollowEvent.ToggleSortType(SortType.DATE_DESC))
                            toggleMenuExpanded(FollowEvent.ToggleMenuExpanded)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("按时间升序") },
                        onClick = {
                            toggleSortType(FollowEvent.ToggleSortType(SortType.DATE_ASC))
                            toggleMenuExpanded(FollowEvent.ToggleMenuExpanded)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("按名称降序") },
                        onClick = {
                            toggleSortType(FollowEvent.ToggleSortType(SortType.NAME_DESC))
                            toggleMenuExpanded(FollowEvent.ToggleMenuExpanded)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("按名称升序") },
                        onClick = {
                            toggleSortType(FollowEvent.ToggleSortType(SortType.NAME_ASC))
                            toggleMenuExpanded(FollowEvent.ToggleMenuExpanded)
                        }
                    )
                }
            }

            IconButton( onClick = { navController.navigate("search") } ) {
                Icon(Icons.Outlined.PersonAdd, "search")
            }
        }
    )
}