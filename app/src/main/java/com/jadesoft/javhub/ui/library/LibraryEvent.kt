package com.jadesoft.javhub.ui.library

import com.jadesoft.javhub.data.model.Movie

sealed class LibraryEvent {
    object LoadItems : LibraryEvent()
    object DeleteItems : LibraryEvent()
    object OnUnSelect : LibraryEvent()
    object OnSelectAll : LibraryEvent()
    object OnReverseSelect : LibraryEvent()
    data class OnSelect(val movie: Movie) : LibraryEvent()
    object OnToggleShowDialog : LibraryEvent()
    object OnToggleDrawerOpen : LibraryEvent()
}