package com.jadesoft.javhub.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun ActressAvatar(
    actress: Actress,
    size: Dp = 0.dp,
    onClick: (String, String, Boolean, ListType) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = if (size != 0.dp) Modifier.size(size) else Modifier,
            shape = CircleShape,
        ) {
            MyAsyncImage(
                imageUrl = actress.avatar,
                modifier = Modifier.fillMaxSize().clickable {
                    onClick(actress.code, actress.name, actress.censored, ListType.Actress)
                },
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = actress.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}