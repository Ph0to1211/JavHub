package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.presentation.common.ActressAvatar

@Composable
fun DetailActressBar(
    actresses: List<Actress>,
    onClick: (String, String, String, Boolean) -> Unit
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

