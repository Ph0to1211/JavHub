package com.jadesoft.javhub.ui.video

sealed class VideoEvent {
    object ToggleFullScreen : VideoEvent()
}