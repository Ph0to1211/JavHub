package com.jadesoft.javhub.presentation.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun HistoryMovieItem(
    movie: Movie,
    isBlurred: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(70.dp)
                .aspectRatio(0.7f)
        ) {
            MyAsyncImage(
                imageUrl = movie.cover,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() },
                contentScale = ContentScale.Crop,
                isBlurred = isBlurred
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
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

        IconButton(
            onClick = { onDelete() },
        ) {
            Icon(Icons.Outlined.Delete, "delete history")
        }
    }
}