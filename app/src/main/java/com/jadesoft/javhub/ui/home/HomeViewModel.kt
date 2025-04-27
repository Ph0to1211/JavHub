package com.jadesoft.javhub.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var currentRoute by mutableStateOf("library")
        private set

    fun updateCurrentRoute(route: String) {
        currentRoute = route
    }

}