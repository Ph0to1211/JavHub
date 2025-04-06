package com.jadesoft.javhub.presentation.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.widget.MyAsyncImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieCardWithBottomTitle(movie: Movie, isBlurred: Boolean, onClick: () -> Unit) {
    SharedTransitionLayout {
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Card(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .aspectRatio(0.7f)
            ) {
                Box {
                    MyAsyncImage(
                        imageUrl = movie.cover,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .clickable { onClick() }
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        isBlurred = isBlurred
                    )

                    Text(
                        text = movie.code,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }

            Text(
                text = movie.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}