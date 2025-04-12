package com.jadesoft.javhub.ui.search

sealed class SearchEvent {
    data class ValueChange(val text: String) : SearchEvent()
    object ClearQuery : SearchEvent()
    object LoadItems : SearchEvent()
    data class SubmitSearch(val saveHistory: Boolean) : SearchEvent()
    object ToggleMag : SearchEvent()
    object RefreshData : SearchEvent()
    object RefreshFilter : SearchEvent()
    data class ModifierType(val index: Int) : SearchEvent()
    object GetSearchHistory : SearchEvent()
    data class InsertSearchHistory(val query: String) : SearchEvent()
    data class DeleteSingleSearchHistory(val query: String) : SearchEvent()
    object DeleteSearchHistory : SearchEvent()
    data class OnHistoryItemClicked(val query: String) : SearchEvent()
}