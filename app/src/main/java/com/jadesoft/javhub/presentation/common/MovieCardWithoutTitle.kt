package com.jadesoft.javhub.presentation.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.widget.MyAsyncImage

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalFoundationApi::class)
@Composable
fun MovieCardWithoutTitle(
    movie: Movie,
    isBlurred: Boolean,
    isSelected: Boolean = false,
    onlySingleClick: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    SharedTransitionLayout {
        Card(
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(
                width = 3.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
        ) {
            MyAsyncImage(
                imageUrl = movie.cover,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                isBlurred = isBlurred,
                modifier = Modifier
                    .fillMaxSize()
                    .combinedClickable(
                        onClick = if (onlySingleClick) onLongClick else onClick,
                        onLongClick = onLongClick
                    )

            )
        }
    }
}