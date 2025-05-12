package com.jadesoft.javhub.ui.actress

sealed class ActressEvent {
    object OnAddFollow : ActressEvent()
    object OnRemoveFollow : ActressEvent()
}