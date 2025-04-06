package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.detail.DetailEvent
import com.jadesoft.javhub.ui.detail.DetailViewModel
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun DetailPreviewBar(
    images: List<String>,
    toggleImageList: (DetailEvent.ToggleImageList) -> Unit,
    toggleImageIndex: (DetailEvent.ToggleImageIndex) -> Unit,
    toggleShowViewer: (DetailEvent.ToggleShowViewer) -> Unit,
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        itemsIndexed(images) { index, item ->
            Card(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .clickable { }
                    .width(250.dp)
                    .aspectRatio(16f / 9f)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                ) {
                    MyAsyncImage(
                        imageUrl = item,
                        contentDescription = item,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                toggleImageList(DetailEvent.ToggleImageList(images))
                                toggleImageIndex(DetailEvent.ToggleImageIndex(index))
                                toggleShowViewer(DetailEvent.ToggleShowViewer)
                            },
                    )
                }
            }
        }
    }
}