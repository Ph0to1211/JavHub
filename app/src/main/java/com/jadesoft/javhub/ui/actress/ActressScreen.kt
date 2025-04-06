package com.jadesoft.javhub.ui.actress

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun ActressScreen(
    code: String,
    name: String,
    censoredType: Boolean,
    navController: NavController,
    actressViewModel: ActressViewModel = hiltViewModel<ActressViewModel>()
) {

}