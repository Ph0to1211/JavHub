package com.jadesoft.javhub.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NoDataTip(tip: String = "", canRefresh: Boolean, refresh: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "(╯°□°）╯︵ ┻━┻",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(15.dp)
        )
        if (tip != "") {
            Text(
                text = tip,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(10.dp)
            )
        }
        if (canRefresh) {
            Button(
                onClick = refresh
            ) {
                Text("重试")
            }
        }
    }
}