package com.jadesoft.javhub.ui.tag

import com.jadesoft.javhub.data.model.Tag

data class TagState(
    val originTag: String,
    val tags: List<Tag> = listOf(),
    val currentDialog: DialogType? = null
)

sealed class DialogType {
    object Add : DialogType()
    data class Edit(val index: Int) : DialogType()
    data class Delete(val index: Int, val name: String) : DialogType()
}