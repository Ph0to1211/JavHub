package com.jadesoft.javhub.presentation.explore

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.ui.explore.ExploreEvent
import com.jadesoft.javhub.ui.explore.ExploreViewModel
import kotlinx.coroutines.launch
import java.util.Locale.Category
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreBottomSheet(
//    modifier: Modifier,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    isLoading: Boolean,
    genres: Map<String, List<Genre>>,
    onlyShowMag: Boolean,
    showUncensored: Boolean,
    itemStyle: Int,
    itemNum: Int,
    filterOptions: List<Genre>,
    resetFilterOption: (ExploreEvent.ResetFilter) -> Unit,
    submitFilterOption: (ExploreEvent.SubmitFilter) -> Unit,
    toggleGenre: (ExploreEvent.ToggleGenre) -> Unit,
    toggleMag: (ExploreEvent.ToggleMag) -> Unit,
    editFilterOptions: (ExploreEvent.EditFilterOption) -> Unit,
    toggleItemStyle: (ExploreEvent.ToggleItemStyle) -> Unit,
    toggleItemNum: (ExploreEvent.ToggleItemNum) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
            ) {
                Tab(
                    text = { Text("分类") },
                    selected = pagerState.currentPage == 0,
                    onClick = {coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }},
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
                Tab(
                    text = { Text("显示") },
                    selected = pagerState.currentPage == 1,
                    onClick = {coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }},
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> ExploreSheetCategoryBar(
                    isLoading = isLoading,
                    genres = genres,
                    onlyShowMag = onlyShowMag,
                    showUncensored = showUncensored,
                    filterOptions = filterOptions,
                    resetFilterOption = resetFilterOption,
                    submitFilterOption = submitFilterOption,
                    toggleGenre = toggleGenre,
                    toggleMag = toggleMag,
                    editFilterOptions = editFilterOptions
                )
                1 -> ExploreSheetDisplayBar(
                    itemStyle = itemStyle,
                    itemNum = itemNum,
                    toggleItemStyle = toggleItemStyle,
                    toggleItemNum = toggleItemNum
                )
            }
        }
    }
}

//@Composable
//fun CategoryBar(
//    isLoading: Boolean,
//    genres: Map<String, List<Genre>>,
//    onlyShowMag: Boolean,
//    showUncensored: Boolean,
//    resetFilterOption: (ExploreEvent.onResetFilterOption) -> Unit,
//    submitFilterOption: (ExploreEvent.onSubmitFilterOption) -> Unit,
//    toggleGenre: (ExploreEvent.onToggleGenre) -> Unit,
//    toggleMag: (ExploreEvent.onToggleMag) -> Unit
//) {
//    val options = listOf("🐎 有码", "🚶‍♀️ 无码")
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(20.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            TextButton(
//                onClick = {
//                    resetFilterOption(
//                        ExploreEvent.onResetFilterOption
//                    )
//                }
//            ) {
//                Text("重置")
//            }
//            Spacer(modifier = Modifier.weight(1f))
//            Button(
//                onClick = {
//                    submitFilterOption(
//                        ExploreEvent.onSubmitFilterOption
//                    )
//                }
//            ) {
//                Text("筛选")
//            }
//        }
//        Text(
//            text = "主要类别",
//            color = MaterialTheme.colorScheme.secondary,
//            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//        )
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(10.dp)
//        ) {
//            SingleChoiceSegmentedButtonRow(
//                modifier = Modifier.width(200.dp)
//            ) {
//                options.forEachIndexed{ index, label ->
//                    SegmentedButton(
//                        shape = SegmentedButtonDefaults.itemShape(
//                            index = index,
//                            count = options.size
//                        ),
//                        onClick = {
//                            toggleGenre(
//                                ExploreEvent.onToggleGenre
//                            )
//                        },
//                        selected = (index == 0) != showUncensored,
//                        label = { Text(label) }
//                    )
//                }
//            }
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Checkbox(
//                    checked = onlyShowMag,
//                    onCheckedChange = {
//                        toggleMag(
//                            ExploreEvent.onToggleMag
//                        )
//                    }
//                )
//                Text("已有磁力", style = MaterialTheme.typography.bodyMedium)
//            }
//        }
//
//        Text(
//            text = "次要类别",
//            color = MaterialTheme.colorScheme.secondary,
//            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//        )
//        SubCategoryBar(isLoading, genres, )
//    }
//}

//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun SubCategoryBar(
//    isLoading: Boolean,
//    genres: Map<String, List<Genre>>,
//    filterOptions: List<Genre>,
//    editFilterOptions: (ExploreEvent.onEditFilterOptions) -> Unit
//) {
//    if (isLoading) {
//        CircularLoading()
//    } else {
//        LazyColumn{
//            genres.forEach { (mainGenre, subGenres) ->
//                item(key = mainGenre) {
//                    Text(
//                        text = mainGenre,
//                        color = MaterialTheme.colorScheme.secondary,
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                item {
//                    FlowRow(
//                        modifier = Modifier.padding(bottom = 12.dp),
//                        horizontalArrangement = Arrangement.spacedBy(8.dp), // 水平间距
//                    ) {
//                        subGenres.forEach { genre ->
//                            key(genre.subGenre) {
//                                FilterChip(
//                                    onClick = {
//                                        editFilterOptions(
//                                            ExploreEvent.onEditFilterOptions(genre = genre)
//                                        )
//                                    },
//                                    label = { Text(genre.subGenre) },
//                                    selected = filterOptions.contains(genre),
//                                    leadingIcon = if (filterOptions.contains(genre)) {
//                                        {
//                                            Icon(
//                                                imageVector = Icons.Filled.Done,
//                                                contentDescription = "Done icon",
//                                                modifier = Modifier.size(18.dp)
//                                            )
//                                        }
//                                    } else null
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun DisplayBar(
//    itemStyle: Int,
//    toggleItemStyle: (ExploreEvent.onToggleItemStyle) -> Unit
//) {
//    val options = listOf("紧凑卡片", "松散卡片", "封面卡片", "列表")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text(
//            text = "显示模式",
//            color = MaterialTheme.colorScheme.secondary,
//            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//        )
//
//        FlowRow(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            options.forEachIndexed { index, option ->
//                FilterChip(
//                    onClick = { toggleItemStyle(
//                        ExploreEvent.onToggleItemStyle(option = index)
//                    ) },
//                    label = { Text(option) },
//                    selected = itemStyle == index,
//                    leadingIcon = if (itemStyle == index) {
//                        {
//                            Icon(
//                                imageVector = Icons.Filled.Done,
//                                contentDescription = "Done icon",
//                                modifier = Modifier.size(18.dp)
//                            )
//                        }
//                    } else null
//                )
//            }
//        }
//
//        Text(
//            text = "显示条数",
//            color = MaterialTheme.colorScheme.secondary,
//            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//        )
//        SteppedSlider()
//    }
//}

//@Composable
//fun SteppedSlider() {
//    val valueRange = 1f..4f
//    val steps = 3
//
//    var sliderValue by remember { mutableStateOf(3f) }
//
//    Column {
//        // 滑块组件
//        Slider(
//            value = sliderValue,
//            onValueChange = { sliderValue = it },
//            valueRange = valueRange,
//            steps = steps,
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Row(
//            modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            for (i in 0..steps + 1) {
//                Text(
//                    text = "${(valueRange.start + i * (valueRange.endInclusive - valueRange.start) / steps).toInt()}",
//                    color = Color.Gray
//                )
//            }
//        }
//    }
//}
