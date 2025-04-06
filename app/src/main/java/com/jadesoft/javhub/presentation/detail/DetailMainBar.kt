package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.detail.DetailEvent
import com.jadesoft.javhub.widget.MyAsyncImage
import com.jadesoft.javhub.widget.SelectableText

@Composable
fun DetailMainBar(
    coverUrl: String,
    bigCover: String,
    title: String,
    code: String,
    isAdded: Boolean,
    deleteFromLibrary: (DetailEvent.DeleteToLibrary) -> Unit,
    toggleImageList: (DetailEvent.ToggleImageList) -> Unit,
    toggleImageIndex: (DetailEvent.ToggleImageIndex) -> Unit,
    toggleShowViewer: (DetailEvent.ToggleShowViewer) -> Unit,
    toggleShowDialog: (DetailEvent.ToggleShowDialog) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(0.7f)
        ) {
            MyAsyncImage(
                imageUrl = coverUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().clickable {
                    toggleImageList(DetailEvent.ToggleImageList(listOf(bigCover, coverUrl)))
                    toggleImageIndex(DetailEvent.ToggleImageIndex(0))
                    toggleShowViewer(DetailEvent.ToggleShowViewer)
                },
            )
        }
        Spacer( modifier = Modifier.width(16.dp) )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            SelectableText(code, style = MaterialTheme.typography.headlineSmall)
            SelectableText(title, style = MaterialTheme.typography.bodyMedium)
            if (!isAdded) {
                Button(
                    onClick = {
                        toggleShowDialog(DetailEvent.ToggleShowDialog)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("添加到库")
                }
            } else {
                OutlinedButton(
                    onClick = {
                        deleteFromLibrary(DetailEvent.DeleteToLibrary)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("已在库中")
                }
            }
        }
    }
}