package com.jadesoft.javhub.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.navigation.TypedMovieRoute

@Composable
fun ActressList(
    actresses: List<Actress>,
    isLoading: Boolean,
    censoredType: Boolean,
    scrollState: LazyGridState,
    navController: NavController,
) {
    if (actresses.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = scrollState,
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(actresses) { index, actress ->
                key(index) {
                    ActressAvatar(
                        actress = actress,
                        onClick = { code, name, censoredType, listType ->
                            navController.navigate(
                                route = TypedMovieRoute(
                                    code = code,
                                    name = name,
                                    actressCode = actress.code,
                                    censoredType = censoredType,
                                    listType = listType
                                )
                            )
                    })
                }
            }

            if (isLoading) {
                item(span = { GridItemSpan(3) }) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .padding(16.dp)
                    )
                }
            }
        }
    } else {
        NoDataTip("无结果", false)
    }
}