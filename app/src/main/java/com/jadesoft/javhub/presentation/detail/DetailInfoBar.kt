package com.jadesoft.javhub.presentation.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.MovieDetail
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.widget.SelectableText

@Composable
fun DetailInfoBar(
    info: MovieDetail,
    onClick: (String, String, Boolean, ListType) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        DetailTextRow(label = "标题", value = info.title)
        DetailTextRow(label = "识别码", value = info.code)
        DetailTextRow(label = "发布日期", value = info.publishDate)
        DetailTextRow(label = "长度", value = info.duration)
        if ((info.director?.code ?: "") != "") {
            info.director?.let { DetailButtonRow(label = "导演", value = it.name, code = it.code, censored = info.censored, onClick = onClick) }
        }
        if ((info.producer?.code ?: "") != "") {
            info.producer?.let { DetailButtonRow(label = "制作商", value = it.name, code = it.code, censored = info.censored, onClick = onClick) }
        }
        if ((info.publisher?.code ?: "") != "") {
            info.publisher?.let { DetailButtonRow(label = "发行商", value = it.name, code = it.code, censored = info.censored, onClick = onClick) }
        }
        if ((info.series?.code ?: "") != "") {
            info.series?.let { DetailButtonRow(label = "系列", value = it.name, code = it.code, censored = info.censored, onClick = onClick) }
        }
    }
}

@Composable
fun DetailTextRow(label: String, value: String, color: Color = Color.Unspecified) {
    Row {
        Text(label, Modifier.weight(1f))
        Text(value, color = color, modifier = Modifier.weight(3f))
    }
}

@Composable
fun DetailButtonRow(
    label: String,
    value: String,
    code: String,
    censored: Boolean,
    onClick: (String, String, Boolean, ListType) -> Unit
){
    val listType = when (label) {
        "导演" -> ListType.Director
        "制作商" -> ListType.Producer
        "发行商" -> ListType.Publisher
        "系列" -> ListType.Series
        else -> ListType.Null
    }
    Row {
        Text(label, Modifier.weight(1f))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.weight(3f).clickable {
                onClick(code, value, censored, listType)
            }
        )
    }
}