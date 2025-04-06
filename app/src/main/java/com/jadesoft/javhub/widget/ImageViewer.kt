package com.jadesoft.javhub.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size.Companion.ORIGINAL
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.mxalbert.zoomable.Zoomable
import com.mxalbert.zoomable.rememberZoomableState
import kotlinx.coroutines.launch
import kotlin.math.abs

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageViewer(
    images: List<String>,
    currentIndex: Int,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
//    val activity = context as Activity
    var currentPage by remember { mutableIntStateOf(currentIndex) }
    val pagerState = rememberPagerState(initialPage = currentIndex, pageCount = { images.size })
    val savedZoomStates = rememberSaveable { mutableMapOf<Int, Float>() }

//    DisposableEffect(Unit) {
//        hideStatusBar(activity)  // 显示对话框时隐藏状态栏
//        onDispose {
//            showStatusBar(activity)  // 对话框关闭时恢复状态栏
//        }
//    }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.65f))
    ) {
        Box(Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->  
                val zoomableState = rememberZoomableState(
                    initialScale = savedZoomStates[page] ?: 1f,
                    maxScale = 3f
                )
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .data(images[page])
                        .size(ORIGINAL)
                        .crossfade(300)
                        .build()
                )
                // 保存缩放状态
                DisposableEffect(page) {
                    onDispose {
                        savedZoomStates[page] = zoomableState.scale
                    }
                }

                // 手势处理
                val coroutineScope = rememberCoroutineScope()
                var enableSwipe by remember { mutableStateOf(true) }

                Zoomable(
                    state = zoomableState,
                    modifier = Modifier.fillMaxSize()
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { change, _ ->
                                if (zoomableState.scale == 1f) {
                                    coroutineScope.launch {
                                        val delta = change.position.x - change.previousPosition.x
                                        if (abs(delta) > 20.dp.toPx()) {
                                            if (delta > 0) {
                                                pagerState.animateScrollToPage(page - 1)
                                            } else {
                                                pagerState.animateScrollToPage(page + 1)
                                            }
                                        }
                                    }
                                }
                            }
                        },
                    onTap = { if (zoomableState.scale == 1f) onDismiss() }
                ) {
                    when (painter.state.value) {
                        is AsyncImagePainter.State.Loading -> {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        is AsyncImagePainter.State.Error -> {
//                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                                Image(
//                                    painter = painterResource(R.drawable.ic_error),
//                                    contentDescription = "Error",
//                                    modifier = Modifier.size(64.dp)
//                                )
//                            }
                        }
                        else -> {
                            val headers = NetworkHeaders.Builder()
                                .add("Referer", "https://www.javbus.com/")
                                .build()

                            val imageRequest = ImageRequest.Builder(LocalContext.current)
                                .data(images[page])
                                .httpHeaders(headers)
                                .build()

                            AsyncImage(
                                model = imageRequest,
                                contentDescription = "Image $page",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
                // 监听缩放状态
                LaunchedEffect(zoomableState.scale) {
                    enableSwipe = zoomableState.scale == 1f
                }
            }
            // 指示器
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 15.dp),

            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                    Box(
                        modifier = Modifier
                            .padding(3.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }
    }
}

// 扩展函数：隐藏状态栏
private fun hideStatusBar(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.insetsController?.apply {
            hide(WindowInsets.Type.statusBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else {
        activity.window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }
}

// 扩展函数：恢复状态栏
private fun showStatusBar(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        activity.window.insetsController?.show(WindowInsets.Type.statusBars())
    } else {
        activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}