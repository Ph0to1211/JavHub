package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.navigation.MovieRoute
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun DetailSimilarBar(movies: List<Movie>, navController: NavController) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { item ->
            Card(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .width(120.dp)
                    .aspectRatio(0.7f)
            ) {
                Box {
                    MyAsyncImage(
                        imageUrl = item.cover,
                        contentDescription = item.title,
                        modifier = Modifier
//                        .sharedElement(
//                            rememberSharedContentState(key = "image-$key"),
//                            animatedVisibilityScope = AnimatedContentScope
//                        )
                            .clickable { navController.navigate(
                                route = MovieRoute(item.code, item.cover, item.title)
                            ) }
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // 标题（左下角）
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                    )

                    // 代码（右上角）
                    Text(
                        text = item.code,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}