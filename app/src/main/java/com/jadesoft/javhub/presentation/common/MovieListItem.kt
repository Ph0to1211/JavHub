package com.jadesoft.javhub.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListItem(
    movie: Movie,
    isBlurred: Boolean,
    isSelected: Boolean = false,
    onlySingleClick: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .border(
                BorderStroke(
                    width = 3.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                )
            )
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(90.dp)
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
                ),
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = movie.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(Modifier.height(3.dp))
            Text(
                text = movie.code,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}