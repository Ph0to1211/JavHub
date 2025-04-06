package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun DetailTrailerBar(url: String) {
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxSize()
            .height(150.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MyAsyncImage(
                imageUrl = url,
                contentDescription = "",
                modifier = Modifier.fillMaxSize().blur(2.dp),
                contentScale = ContentScale.Crop
            )
            FloatingActionButton(
                onClick = {  },
                modifier = Modifier.align(Alignment.Center),
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = ""
                )
            }
        }
    }
}