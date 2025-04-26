package com.jadesoft.javhub.ui.setting

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jadesoft.javhub.presentation.setting.SettingScaffold
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    navController: NavController,
    settingViewModel: SettingViewModel = hiltViewModel<SettingViewModel>()
) {

    val settingNavController = rememberNavController()

    val scrollState = rememberScrollState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        canScroll = { true }
    )

    val settingState = settingViewModel.settingState.collectAsState()
    val isPublic = settingState.value.isPublic
    val isStealth = settingState.value.isStealth

    NavHost(
        navController = settingNavController,
        startDestination = SettingRoutes.Main
    ) {

        composable<SettingRoutes.Main> {
            SettingScaffold(
                scrollState = scrollState,
                scrollBehavior = scrollBehavior,
                navController = settingNavController,
                navigateBack = { navController.popBackStack() }
            )
        }

        composable<SettingRoutes.General> {
            GeneralScreen(
                isPublic = isPublic,
                isStealth = isStealth,
                onTogglePublic = settingViewModel::onEvent,
                onToggleStealth = settingViewModel::onEvent,
                navigateBack = { settingNavController.popBackStack() }
            )
        }

        composable<SettingRoutes.Appearance> {
            AppearanceScreen(
                navigateBack = { settingNavController.popBackStack() }
            )
        }

        composable<SettingRoutes.Storage> {
            StorageScreen(
                navigateBack = { settingNavController.popBackStack() }
            )
        }

        composable<SettingRoutes.Privacy> {
            PrivacyScreen(
                navigateBack = { settingNavController.popBackStack() }
            )
        }

        composable<SettingRoutes.About> {
            AboutScreen(
                navigateBack = { settingNavController.popBackStack() }
            )
        }

    }
}

@Serializable
sealed class SettingRoutes {
    @Serializable
    object Main : SettingRoutes()

    @Serializable
    object General : SettingRoutes()

    @Serializable
    object Appearance : SettingRoutes()

    @Serializable
    object Storage : SettingRoutes()

    @Serializable
    object Privacy : SettingRoutes()

    @Serializable
    object About : SettingRoutes()
}