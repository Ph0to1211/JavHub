package com.jadesoft.javhub.widget

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier,
    showControls: Boolean = true,
    isFullScreen: Boolean = false,
    onFullScreenClick: () -> Unit = {}
) {
    val context = LocalContext.current

    // 处理屏幕方向
    DisposableEffect(isFullScreen) {
        val activity = context as Activity
        val originalOrientation = activity.requestedOrientation

        if (isFullScreen) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onDispose {
            activity.requestedOrientation = originalOrientation
        }
    }

    // 播放器视图
    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .clickable { onFullScreenClick() },
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = showControls
                setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                setFullscreenButtonClickListener { onFullScreenClick() }
            }
        }
    )

    // 处理系统 UI
    DisposableEffect(isFullScreen) {
        val window = (context as Activity).window
        if (isFullScreen) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }

        onDispose {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }
}
