package com.jadesoft.javhub.ui.follow

sealed class FollowEvent {
    object OnRemoveFollow : FollowEvent()
    data class ToggleShowDialog(val code: String? = null) : FollowEvent()
    object ToggleMenuExpanded : FollowEvent()
    data class ToggleSortType(val type: SortType) : FollowEvent()
}