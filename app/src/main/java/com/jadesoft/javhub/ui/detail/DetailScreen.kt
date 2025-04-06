package com.jadesoft.javhub.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Factory
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material.icons.outlined.Woman
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.jadesoft.javhub.data.model.Link
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.model.MovieDetail
import com.jadesoft.javhub.navigation.GenreRoute
import com.jadesoft.javhub.navigation.Home
import com.jadesoft.javhub.navigation.MovieRoute
import com.jadesoft.javhub.navigation.TypedMovieRoute
import com.jadesoft.javhub.presentation.common.CircularLoading
import com.jadesoft.javhub.presentation.detail.DetailActressBar
import com.jadesoft.javhub.presentation.detail.DetailBackgroundCover
import com.jadesoft.javhub.presentation.detail.DetailCategoryBar
import com.jadesoft.javhub.presentation.detail.DetailDialog
import com.jadesoft.javhub.presentation.detail.DetailInfoBar
import com.jadesoft.javhub.presentation.detail.DetailListTile
import com.jadesoft.javhub.presentation.detail.DetailMainBar
import com.jadesoft.javhub.presentation.detail.DetailPreviewBar
import com.jadesoft.javhub.presentation.detail.DetailSimilarBar
import com.jadesoft.javhub.presentation.detail.DetailTopAppBar
import com.jadesoft.javhub.presentation.detail.DetailTrailerBar
import com.jadesoft.javhub.widget.ImageViewer
import com.jadesoft.javhub.widget.MyAsyncImage
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
fun DetailScreen(
    code: String,
    title: String,
    coverUrl: String,
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel<DetailViewModel>()
) {
    val scrollState = rememberScrollState()

    val isPullDown by derivedStateOf {
        scrollState.value >= 300
    }

    val detailState = detailViewModel.detailState.collectAsState()

    val movie = detailState.value.movie
    val isLoading = detailState.value.isLoading
    val isAdded = detailState.value.isAdded
    val censoredType = detailState.value.censoredType
    val isStealth = detailState.value.isStealth
    val imageList = detailState.value.imageList
    val currentImageIndex = detailState.value.currentImageIndex
    val showImageViewer = detailState.value.showImageViewer
    val showDialog = detailState.value.showDialog
    val isUserAction = detailState.value.isUserAction
    val tags = detailState.value.tags
    val selectedTags = detailState.value.selectedTags

    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (movie == null) {
            detailViewModel.onEvent(DetailEvent.LoadMovie(code))
        }
        detailViewModel.onEvent(DetailEvent.IsMovieExisted(code))
    }

    LaunchedEffect(movie) {
        if (movie != null && !isStealth) {
            detailViewModel.onEvent(DetailEvent.AddToHistory(coverUrl))
        }
    }

    LaunchedEffect(isAdded) {
        if (isUserAction) {
            val message = if (isAdded) "已添加到库" else "已从库移除"

            snackbarHostState.showSnackbar(
                message = message
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            DetailTopAppBar(
                isPullDown,
                movie?.title ?: "",
                cover = coverUrl,
                tags = selectedTags,
                isAdded = isAdded,
                onPop = {navController.popBackStack()},
                onNavigate = {navController.navigate(Home) {
                    launchSingleTop = true
                }},
                addToLibrary = detailViewModel::onEvent,
                deleteFromLibrary = detailViewModel::onEvent
            )
        }
    ) { innerPadding ->
        Box {
            if (!isPullDown) if (movie != null) {
                DetailBackgroundCover(
                    img = movie.bigCover,
                    detailViewModel::onEvent,
                    detailViewModel::onEvent,
                    detailViewModel::onEvent
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(Modifier.height(10.dp))

                DetailMainBar(
                    coverUrl = coverUrl,
                    bigCover = movie?.bigCover ?: "",
                    title = title,
                    code = code,
                    isAdded = isAdded,
                    deleteFromLibrary = detailViewModel::onEvent,
                    toggleImageIndex = detailViewModel::onEvent,
                    toggleShowViewer = detailViewModel::onEvent,
                    toggleImageList = detailViewModel::onEvent,
                    toggleShowDialog = detailViewModel::onEvent
                )

                if (movie != null) {
                    DetailListTile("分类")
                    movie.genres?.let { DetailCategoryBar(
                        categories = it,
                        onClick = { code, name, censoredType, listType ->
                            navController.navigate(
                                route = TypedMovieRoute(
                                    code = code,
                                    name = name,
                                    censoredType = censoredType,
                                    listType = listType
                                )
                            )
                        }
                    ) }

                    if (movie.images?.isNotEmpty() == true) {
                        DetailListTile("预览")
                        DetailPreviewBar(movie.images, detailViewModel::onEvent, detailViewModel::onEvent, detailViewModel::onEvent)
                    }

                    DetailListTile("预告片")
                    DetailTrailerBar(movie.bigCover)

                    DetailListTile("详情")
                    DetailInfoBar(
                        info = movie,
                        onClick = { code, name, censoredType, listType ->
                            navController.navigate(
                                route = TypedMovieRoute(
                                    code = code,
                                    name = name,
                                    censoredType = censoredType,
                                    listType = listType
                                )
                            )
                        }
                    )

                    if (movie.actress?.isNotEmpty() == true) {
                        DetailListTile("演员")
                        DetailActressBar(
                            actresses = movie.actress,
                            censoredType = censoredType,
                            onClick = { code, name, censoredType, listType ->
                                navController.navigate(
                                    route = TypedMovieRoute(
                                        code = code,
                                        name = name,
                                        censoredType = censoredType,
                                        listType = listType
                                    )
                                )
                            }
                        )
                    }

                    DetailListTile("相似推荐")
                    movie.similarMovies?.let { DetailSimilarBar(
                        movies = it,
                        navController = navController
                    ) }
                    Spacer(Modifier.height(10.dp))
                } else {
                    Spacer(Modifier.height(200.dp))
                    CircularLoading()
                }
            }

            DetailDialog(
                cover = coverUrl,
                showDialog = showDialog,
                onDismiss = detailViewModel::onEvent,
                onConfirm = detailViewModel::onEvent,
                editTags = detailViewModel::onEvent,
                tags = tags,
                selectedTags = selectedTags
            )

            if (showImageViewer) {
                ImageViewer(
                    images = imageList,
                    currentIndex = currentImageIndex,
                    onDismiss = { detailViewModel.onEvent(DetailEvent.ToggleShowViewer)}
                )
            }
        }
    }
}


//@Preview
//@Composable
//fun ScreenPreview() {
//    DetailScreen(
//        movie = Movie(code = "START-253", title = "成人式マジックで大人の階段を駆け上った夜。元陰キャな僕たちは同窓会を抜け出し、朝まで何度も何度もSEXした。宮島めい", cover = "https://www.javbus.com/pics/thumb/b35z.jpg", url = "https://www.javbus.com/START-253"),
//        navController = rememberNavController()
//    )
//}