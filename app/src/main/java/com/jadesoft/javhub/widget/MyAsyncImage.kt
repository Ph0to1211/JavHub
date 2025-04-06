package com.jadesoft.javhub.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest

@Composable
fun MyAsyncImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1f,
    isBlurred: Boolean = false,
    contentDescription: String = "",
) {
    val headers = NetworkHeaders.Builder()
        .add("Referer", "https://www.javbus.com/")
        .build()

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .httpHeaders(headers)
        .build()

    Box(
        modifier = if (isBlurred) Modifier.blur(5.dp) else Modifier
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = contentDescription,
            contentScale = contentScale,
            alpha = alpha,
            modifier = modifier
        )
    }
}