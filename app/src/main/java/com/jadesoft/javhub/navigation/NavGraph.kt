package com.jadesoft.javhub.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jadesoft.javhub.ui.actress.ActressScreen
import com.jadesoft.javhub.ui.detail.DetailScreen
import com.jadesoft.javhub.ui.follow.FollowScreen
import com.jadesoft.javhub.ui.genre.GenreScreen
import com.jadesoft.javhub.ui.home.HomeScreen
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.ui.movie.MovieScreen
import com.jadesoft.javhub.ui.search.SearchScreen
import com.jadesoft.javhub.ui.setting.SettingScreen
import com.jadesoft.javhub.ui.tag.TagScreen
import com.jadesoft.javhub.ui.video.VideoScreen
import kotlinx.serialization.Serializable

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    start: Any = Home,
    enterTransition: EnterTransition = fadeIn(animationSpec = tween(durationMillis = 300)),
    exitTransition: ExitTransition = fadeOut(animationSpec = tween(durationMillis = 300)),
) {
    NavHost(
        navController = navController,
        startDestination = start,
        modifier = modifier,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
//        enterTransition = {
//            fadeIn(animationSpec = tween(durationMillis = 300)) +
//                    expandHorizontally(animationSpec = tween(durationMillis = 300))
//        },
//        exitTransition = {
//            fadeOut(animationSpec = tween(durationMillis = 300)) +
//                    shrinkHorizontally(animationSpec = tween(durationMillis = 300))
//        },
    ) {

        composable<Home> { HomeScreen(navController) }

        composable<MovieRoute>(
//            enterTransition = {
//                fadeIn(animationSpec = tween(durationMillis = 300)) +
//                        scaleIn(animationSpec = tween(durationMillis = 300), initialScale = 0.3f)
//            },
//            exitTransition = {
//                fadeOut(animationSpec = tween(durationMillis = 300)) +
//                        scaleOut(animationSpec = tween(durationMillis = 300), targetScale = 0.3f)
//            }
        ) { backStackEntry ->
            val movie: MovieRoute = backStackEntry.toRoute()
            DetailScreen(
                code = movie.code,
                coverUrl = movie.imgUrl,
                title = movie.title,
                navController = navController
            )
        }

        composable<ActressRoute>{ backStackEntry ->
            val actress: ActressRoute = backStackEntry.toRoute()
            ActressScreen(
                code = actress.code,
                name = actress.name,
                avatarUrl = actress.avatarUrl,
                censored = actress.censored,
                navController = navController
            )
        }

        composable<GenreRoute>{ backStackEntry ->
            val genre: GenreRoute = backStackEntry.toRoute()
            GenreScreen(
                code = genre.code,
                name = genre.name,
                type = false,
                navController = navController,
            )
        }

        composable<TypedMovieRoute>{ backStackEntry ->
            val typedMovie: TypedMovieRoute = backStackEntry.toRoute()
            MovieScreen(
                code = typedMovie.code,
                name = typedMovie.name,
                actressCode = typedMovie.actressCode,
                censoredType = typedMovie.censoredType,
                listType = typedMovie.listType,
                navController = navController
            )
        }

        composable<VideoRoute>{ backStackEntry ->
            val video: VideoRoute = backStackEntry.toRoute()
            VideoScreen(
                title = video.title,
                videoUrl = video.url,
                navController = navController
            )
        }

        composable( Destinations.SEARCH ) { SearchScreen(navController) }
        composable( Destinations.SETTING ) { SettingScreen(navController) }
        composable( Destinations.TAG ) { TagScreen(navController) }
        composable( Destinations.FOLLOW ) { FollowScreen(navController) }

    }
}


object Destinations {
    const val SEARCH = "search"
    const val SETTING = "setting"
    const val TAG = "tag"
    const val FOLLOW = "follow"
}


@Serializable
object Home

@Serializable
data class MovieRoute(val code: String, val imgUrl: String, val title: String)

@Serializable
data class ActressRoute(val code: String, val name: String, val avatarUrl: String, val censored: Boolean)

@Serializable
data class TypedMovieRoute(
    val code: String,
    val name: String,
    val actressCode: String = "",
    val censoredType: Boolean,
    val listType: ListType,
)

@Serializable
data class GenreRoute(val code: String, val name: String)

@Serializable
data class VideoRoute(val title: String, val url: String)
