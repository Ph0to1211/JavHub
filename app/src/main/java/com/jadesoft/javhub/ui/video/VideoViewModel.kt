package com.jadesoft.javhub.ui.video

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val context: Context
): ViewModel() {

    private val _videoState = MutableStateFlow(
        VideoState()
    )
    val videoState: StateFlow<VideoState> = _videoState

    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                repeatMode = Player.REPEAT_MODE_OFF
                playWhenReady = false
            }
    }

    // 保存播放状态变量
    private var lastKnownPosition: Long = 0L

    init {
        // 监听播放状态以保存进度
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    lastKnownPosition = exoPlayer.currentPosition
                }
            }
        })
    }

    fun setVideoUrl(url: String) {
        val currentItem = exoPlayer.currentMediaItem
        if (currentItem?.requestMetadata?.mediaUri?.toString() != url) {
            exoPlayer.setMediaItem(MediaItem.fromUri(url))
            exoPlayer.prepare()
            exoPlayer.seekTo(lastKnownPosition)
        }
    }

    fun onEvent(event: VideoEvent) {
        when(event) {
            is VideoEvent.ToggleFullScreen -> handleToggleFullScreen()
        }
    }

    private fun handleToggleFullScreen() {
        updateState { copy(isFullScreen = !isFullScreen) }
    }


    private inline fun updateState(transform: VideoState.() -> VideoState) {
        _videoState.update(transform)
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }

}