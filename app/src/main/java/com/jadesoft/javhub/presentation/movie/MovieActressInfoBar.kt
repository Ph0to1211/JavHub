package com.jadesoft.javhub.presentation.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun MovieActressInfoBar(
    actress: Actress
) {
    Row {
        Card(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
        ) {
            MyAsyncImage(
                imageUrl = actress.avatar,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}