package com.jadesoft.javhub.ui.setting

import androidx.lifecycle.ViewModel
import com.jadesoft.javhub.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val preferences: PreferencesManager
): ViewModel() {

    private val _settingState = MutableStateFlow(
        SettingState(
            isPublic = preferences.publicMode,
            isStealth = preferences.stealthMode
        )
    )
    val settingState: StateFlow<SettingState> = _settingState

    fun onEvent(event: SettingEvent) {
        when(event) {
            is SettingEvent.TogglePublicMode -> handleTogglePublicMode()
            is SettingEvent.ToggleStealthMode -> handleToggleStealthMode()
        }
    }

    private fun handleTogglePublicMode() {
        updateState { copy(isPublic = !isPublic) }
        preferences.publicMode = _settingState.value.isPublic
    }

    private fun handleToggleStealthMode() {
        updateState { copy(isStealth = !isStealth) }
        preferences.stealthMode = _settingState.value.isStealth
    }


    private inline fun updateState(transform: SettingState.() -> SettingState) {
        _settingState.update(transform)
    }

}