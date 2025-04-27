package com.jadesoft.javhub.presentation.more

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.jadesoft.javhub.ui.more.MoreEvent
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoreScaffold(
    context: Context,
    isPublic: Boolean,
    isStealth: Boolean,
    navController: NavController,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onTogglePublicMode: (MoreEvent.TogglePublicMode) -> Unit,
    onToggleStealthMode: (MoreEvent.ToggleStealthMode) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("更多") }
            )
        },
        bottomBar = {
            NavigationBar {  }
        },
        content = { innerPadding ->
            MoreContent(
                context = context,
                isPublic = isPublic,
                isStealth = isStealth,
                navController = navController,
                coroutineScope = coroutineScope,
                innerPadding = innerPadding,
                snackbarHostState = snackbarHostState,
                onTogglePublicMode = onTogglePublicMode,
                onToggleStealthMode = onToggleStealthMode
            )
        }
    )
}