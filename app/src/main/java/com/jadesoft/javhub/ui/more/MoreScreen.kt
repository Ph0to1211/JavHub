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
import com.jadesoft.javhub.presentation.more.MoreScaffold
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

    MoreScaffold(
        context = context,
        isPublic = isPublic,
        isStealth = isStealth,
        navController = navController,
        coroutineScope = coroutineScope,
        snackbarHostState = snackbarHostState,
        onTogglePublicMode = moreViewModel::onEvent,
        onToggleStealthMode = moreViewModel::onEvent
    )
}