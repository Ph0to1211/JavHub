package com.jadesoft.javhub.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HomeRepository @Inject constructor() {
    private val _isDrawerOpen = MutableStateFlow(false)
    val isDrawerOpen: StateFlow<Boolean> = _isDrawerOpen

    fun toggleDrawerOpen() {
        _isDrawerOpen.value = !_isDrawerOpen.value
    }
}