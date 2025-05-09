package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.explore.ExploreEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreTopBar(
    showMenu: Boolean,
    exploreType: Boolean,
    onToggleMenu: (ExploreEvent.ToggleMenu) -> Unit,
    onToggleExploreType: (ExploreEvent.ToggleExploreType) -> Unit,
    onToggleFilter: (ExploreEvent.ToggleFilter) -> Unit,
    onToggleDrawerOpen: (ExploreEvent.ToggleDrawerShow) -> Unit,
    navigateToSearch: () -> Unit
) {
    TopAppBar(
        title = {
            Box {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(60.dp))
                        .clickable { onToggleMenu(ExploreEvent.ToggleMenu) }
                        .padding(start = 10.dp, end = 14.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    if (showMenu) Icon(Icons.Default.ArrowDropDown, "")
                    else Icon(Icons.Default.ArrowDropUp, "")
                    if (exploreType) Text("影片") else Text("女优")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { onToggleMenu(ExploreEvent.ToggleMenu) }
                ) {
                    DropdownMenuItem(
                        text = { Text("影片") },
                        onClick = {
                            onToggleExploreType(
                                ExploreEvent.ToggleExploreType(true)
                            )
                            onToggleMenu(ExploreEvent.ToggleMenu)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("女优") },
                        onClick = {
                            onToggleExploreType(
                                ExploreEvent.ToggleExploreType(false)
                            )
                            onToggleMenu(ExploreEvent.ToggleMenu)
                        }
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onToggleDrawerOpen(ExploreEvent.ToggleDrawerShow) }
            ) {
                Icon(Icons.Default.Menu, "Drawer menu")
            }
        },
        actions = {
            Row {
                IconButton(
                    onClick = navigateToSearch
                ) {
                    Icon(Icons.Default.Search, "search")
                }
                IconButton(
                    onClick = { onToggleFilter(ExploreEvent.ToggleFilter) }
                ) {
                    Icon(Icons.Default.FilterList, "filter")
                }
            }
        }
    )
}