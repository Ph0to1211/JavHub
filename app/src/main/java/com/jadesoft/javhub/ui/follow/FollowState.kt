package com.jadesoft.javhub.ui.follow

import com.jadesoft.javhub.data.model.Actress

data class FollowState (
    val followings: List<Actress> = emptyList(),
    val isShowDialog: Boolean = false,
    val selectedCode: String? = null
)