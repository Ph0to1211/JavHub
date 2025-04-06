package com.jadesoft.javhub.presentation.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.widget.MyAsyncImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieCardWithoutTitle(movie: Movie, isBlurred: Boolean, onClick: () -> Unit) {
    SharedTransitionLayout {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .aspectRatio(0.7f)
        ) {
            MyAsyncImage(
                imageUrl = movie.cover,
                contentDescription = movie.title,
                modifier = Modifier
                    .clickable { onClick() }
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                isBlurred = isBlurred
            )
        }
    }
}