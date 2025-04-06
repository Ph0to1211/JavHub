package com.jadesoft.javhub.ui.more

import androidx.lifecycle.ViewModel
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.ui.library.LibraryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val preferences: PreferencesManager
): ViewModel() {

    private val _moreState = MutableStateFlow(
        MoreState(
            isPublic = preferences.publicMode,
            isStealth = preferences.stealthMode
        )
    )
    val moreState: StateFlow<MoreState> = _moreState

    fun onEvent(event: MoreEvent) {
        when(event) {
            is MoreEvent.TogglePublicMode -> handleTogglePublicMode()
            is MoreEvent.ToggleStealthMode -> handleToggleStealthMode()
        }
    }

    private fun handleTogglePublicMode() {
        updateState { copy(isPublic = !isPublic) }
        preferences.publicMode = _moreState.value.isPublic
    }

    private fun handleToggleStealthMode() {
        updateState { copy(isStealth = !isStealth) }
        preferences.stealthMode = _moreState.value.isStealth
    }


    private inline fun updateState(transform: MoreState.() -> MoreState) {
        _moreState.update(transform)
    }

}