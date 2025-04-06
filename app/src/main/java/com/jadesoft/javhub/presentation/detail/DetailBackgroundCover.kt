package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.detail.DetailEvent
import com.jadesoft.javhub.ui.detail.DetailViewModel
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun DetailBackgroundCover(
    img: String,
    toggleImageList: (DetailEvent.ToggleImageList) -> Unit,
    toggleImageIndex: (DetailEvent.ToggleImageIndex) -> Unit,
    toggleShowViewer: (DetailEvent.ToggleShowViewer) -> Unit,
) {
    val background = MaterialTheme.colorScheme.surface

    MyAsyncImage(
        imageUrl = img,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.95f to background
                    )
                )
            }
            .blur(3.dp)
            .clickable {
                toggleImageList(DetailEvent.ToggleImageList(listOf(img)))
                toggleImageIndex(DetailEvent.ToggleImageIndex(0))
                toggleShowViewer(DetailEvent.ToggleShowViewer)
            },
        contentScale = ContentScale.Crop,
        alpha = 0.5f
    )
}