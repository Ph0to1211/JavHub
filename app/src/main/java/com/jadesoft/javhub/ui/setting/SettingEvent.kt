package com.jadesoft.javhub.ui.setting

sealed class SettingEvent {
    object TogglePublicMode: SettingEvent()
    object ToggleStealthMode: SettingEvent()
}