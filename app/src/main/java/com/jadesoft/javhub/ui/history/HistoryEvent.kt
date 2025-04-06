package com.jadesoft.javhub.ui.history

sealed class HistoryEvent {
    object GetItems : HistoryEvent()
    data class DeleteHistory(val code: String) : HistoryEvent()
}