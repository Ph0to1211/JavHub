package com.jadesoft.javhub.ui.history

sealed class HistoryEvent {
    object GetItems : HistoryEvent()
    object DeleteAllHistory : HistoryEvent()
    object ToggleShowDialog : HistoryEvent()
    object ToggleDrawerOpen : HistoryEvent()
    data class DeleteHistory(val code: String) : HistoryEvent()
}