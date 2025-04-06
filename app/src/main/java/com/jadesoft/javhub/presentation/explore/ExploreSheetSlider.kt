package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.ui.explore.ExploreEvent

@Composable
fun ExploreSheetSlider(
    itemNum: Int,
    toggleItemNum: (ExploreEvent.ToggleItemNum) -> Unit
) {
    Column {
        val sliderValue = itemNum.toFloat()
        // 滑动条组件
        Slider(
            value = sliderValue,
            onValueChange = { newValue ->
                toggleItemNum(
                    ExploreEvent.ToggleItemNum(newValue.toInt())
                )
//                toggleSliderValue(newValue.toInt())
            },
            valueRange = 1f..5f,
            steps = 3,
            modifier = Modifier.fillMaxWidth()
        )

        // 刻度标记
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 1..5) {
                Text(
                    text = i.toString(),
                    color = if (i.toFloat() == sliderValue)
                        MaterialTheme.colorScheme.secondary else Color.Gray,
                )
            }
        }
    }
}
