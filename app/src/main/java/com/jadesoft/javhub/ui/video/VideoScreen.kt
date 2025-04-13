package com.jadesoft.javhub.ui.video

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.widget.VideoPlayer

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoScreen(
    title: String,
    videoUrl: String,
    navController: NavController,
    videoViewModel: VideoViewModel = hiltViewModel()
) {
    // 当视频URL变化时更新播放器
    LaunchedEffect(videoUrl) {
        videoViewModel.setVideoUrl(videoUrl)
    }

    val videoState = videoViewModel.videoState.collectAsState()
    val isFullScreen = videoState.value.isFullScreen

    // 处理返回键
    BackHandler(enabled = isFullScreen) {
        videoViewModel.onEvent(VideoEvent.ToggleFullScreen)
    }

    Scaffold(
        topBar = {
            if (!isFullScreen) {
                TopAppBar(
                    colors = topAppBarColors(
                        MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp)
                    ),
                    title = {
                        Text(text = title)
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                        }
                    },
                )
            }
        },
        containerColor = if (isFullScreen) Color.Black else MaterialTheme.colorScheme. background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            VideoPlayer(
                exoPlayer = videoViewModel.exoPlayer,
                modifier = if (isFullScreen) {
                    Modifier.fillMaxSize()
                } else {
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .align(Alignment.Center)
                },
                isFullScreen = isFullScreen,
                onFullScreenClick = { videoViewModel.onEvent(VideoEvent.ToggleFullScreen) }
            )

            // 全屏状态下的自定义返回按钮
//            if (isFullScreen) {
//                IconButton(
//                    onClick = { videoViewModel.onEvent(VideoEvent.ToggleFullScreen) },
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .align(Alignment.TopStart)
//                ) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = "退出全屏",
//                        tint = Color.White
//                    )
//                }
//            }
        }
    }
}