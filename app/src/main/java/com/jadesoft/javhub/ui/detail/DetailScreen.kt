package com.jadesoft.javhub.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jadesoft.javhub.navigation.Home
import com.jadesoft.javhub.navigation.TypedMovieRoute
import com.jadesoft.javhub.navigation.VideoRoute
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
    val videoUrl = detailState.value.videoUrl
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
    val isVideoUrlInitialized = detailState.value.isVideoUrlInitialized

    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (movie == null) {
            detailViewModel.onEvent(DetailEvent.LoadMovie(code))
        }
        detailViewModel.onEvent(DetailEvent.IsMovieExisted(code))
        if (!isVideoUrlInitialized) {
            detailViewModel.onEvent(DetailEvent.VideoUrlInitialize(code))
        }
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
                        censored = movie.censored,
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

                    if (!isVideoUrlInitialized || videoUrl.isNotEmpty()) {
                        DetailListTile("预告片")
                        DetailTrailerBar(
                            title = code,
                            imgUrl = movie.bigCover,
                            videoUrl = videoUrl,
                            isVideoUrlInitialized = isVideoUrlInitialized,
                            onClick = { title, url ->
                                navController.navigate(
                                    route = VideoRoute(
                                        title = title,
                                        url = url
                                    )
                                )
                            }
                        )
                    }

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
                selectedTags = selectedTags,
                toAdd = { navController.navigate("tag") }
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