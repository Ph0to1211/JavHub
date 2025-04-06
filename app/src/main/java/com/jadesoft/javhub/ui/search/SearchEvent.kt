package com.jadesoft.javhub.ui.search

sealed class SearchEvent {
    data class ValueChange(val text: String) : SearchEvent()
    object ClearQuery : SearchEvent()
    object LoadItems : SearchEvent()
    object SubmitSearch : SearchEvent()
    object ToggleMag : SearchEvent()
    object RefreshData : SearchEvent()
    object RefreshFilter : SearchEvent()
    data class ModifierType(val index: Int) : SearchEvent()
}