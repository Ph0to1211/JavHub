package com.jadesoft.javhub.ui.history

import com.jadesoft.javhub.data.model.History

data class HistoryState(
    val histories: List<History> = emptyList(),
    val count: Int,
    val isBlurred: Boolean = false
)