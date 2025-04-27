package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.navigation.Home
import com.jadesoft.javhub.ui.detail.DetailEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(
    isPullDown: Boolean,
    title: String,
    cover: String,
    tags: List<String>,
    isAdded: Boolean,
    onPop: () -> Unit,
    onNavigate: () -> Unit,
    addToLibrary: (DetailEvent.AddToLibrary) -> Unit,
    deleteFromLibrary: (DetailEvent.DeleteToLibrary) -> Unit,
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = if (isPullDown) MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp)
            else MaterialTheme.colorScheme.surface.copy(0f)
        ),
        title = {
            if (isPullDown) {
                Text(
                    text = title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            Row {
                IconButton(
                    onClick = { onPop() }
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
                IconButton(
                    onClick = { onNavigate() }
                ) {
                    Icon(Icons.Default.Home, contentDescription = "回到首页")
                }
            }
        },
        actions = {
            Row {
                IconButton(
                    onClick = {
                        if (isAdded) {
                            deleteFromLibrary(DetailEvent.DeleteToLibrary)
                        } else {
                            addToLibrary(DetailEvent.AddToLibrary(
                                tags = tags,
                                cover = cover
                            ))
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isAdded) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "收藏"
                    )
                }
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Filled.MoreHoriz, contentDescription = "更多")
                }
            }
        }
    )
}