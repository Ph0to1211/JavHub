package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.presentation.common.ActressAvatar
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.widget.MyAsyncImage

@Composable
fun DetailActressBar(
    actresses: List<Actress>,
    censoredType: Boolean,
    onClick: (String, String, Boolean, ListType) -> Unit
) {

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(actresses) { actress ->
            ActressAvatar(actress, 80.dp, onClick)
        }
    }
}

