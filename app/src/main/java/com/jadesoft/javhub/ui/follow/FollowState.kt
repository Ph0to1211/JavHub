package com.jadesoft.javhub.ui.follow

import com.jadesoft.javhub.data.model.Actress

data class FollowState (
    val followings: List<Actress> = emptyList(),
    val isShowDialog: Boolean = false,
    val isMenuExpanded: Boolean = false,
    val selectedCode: String? = null,
    val currentSort: SortType = SortType.DATE_DESC
)

enum class SortType {
    DATE_DESC,
    DATE_ASC,
    NAME_DESC,
    NAME_ASC
}