package com.jadesoft.javhub.ui.more

sealed class MoreEvent {
    object TogglePublicMode: MoreEvent()
    object ToggleStealthMode: MoreEvent()
}