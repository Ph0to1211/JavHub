package com.jadesoft.javhub.ui.explore

import com.jadesoft.javhub.data.model.Genre

sealed class ExploreEvent {
    object ResetFilter : ExploreEvent()
    object SubmitFilter : ExploreEvent()
    object ToggleGenre : ExploreEvent()
    object ToggleFilter : ExploreEvent()
    data class EditFilterOption(val genre: Genre) : ExploreEvent()
    data class ToggleItemStyle(val option: Int) : ExploreEvent()
    data class ToggleItemNum(val num: Int) : ExploreEvent()
    object ToggleMag : ExploreEvent()
    data class ToggleExploreType(val enabled: Boolean) : ExploreEvent()
    object ToggleMenu : ExploreEvent()
    object ToggleDrawerShow : ExploreEvent()
    object RefreshData : ExploreEvent()
    object ScrollToTop : ExploreEvent()
    object LoadItems : ExploreEvent()
    object LoadGenres : ExploreEvent()
}