package com.jadesoft.javhub.presentation.common

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
fun MovieCard(movie: Movie, isBlurred: Boolean, onClick: () -> Unit) {
    SharedTransitionLayout {
        Card(
            shape = RoundedCornerShape(6.dp),
//            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
        ) {
            Box {
                MyAsyncImage(
                    imageUrl = movie.cover,
                    contentDescription = movie.title,
                    modifier = Modifier
//                        .sharedElement(
//                            rememberSharedContentState(key = "image-$key"),
//                            animatedVisibilityScope = AnimatedContentScope
//                        )
                        .clickable { onClick() }
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    isBlurred = isBlurred
                )

                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
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
    }
}