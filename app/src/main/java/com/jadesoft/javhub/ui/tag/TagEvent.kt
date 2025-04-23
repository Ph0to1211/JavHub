package com.jadesoft.javhub.ui.tag

sealed class TagEvent{
    data class ShowDialog(val type: DialogType) : TagEvent()
    object DismissDialog : TagEvent()
    data class OnAddTag(val name: String) : TagEvent()
    data class OnEditTag(val index: Int, val name: String) : TagEvent()
    data class OnDeleteTag(val index: Int, val name: String) : TagEvent()
    data class OnMoveUpTag(val index: Int) : TagEvent()
    data class OnMoveDownTag(val index: Int) : TagEvent()
}