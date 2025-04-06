package com.jadesoft.javhub.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.jadesoft.javhub.data.model.Actress
import com.jadesoft.javhub.data.model.Genre
import com.jadesoft.javhub.data.model.Movie
import com.jadesoft.javhub.data.preferences.PreferencesManager
import com.jadesoft.javhub.ui.category.CategoryScreen
import com.jadesoft.javhub.ui.detail.DetailScreen
import com.jadesoft.javhub.ui.detail.DetailViewModel
import com.jadesoft.javhub.ui.explore.ExploreScreen
import com.jadesoft.javhub.ui.genre.GenreScreen
import com.jadesoft.javhub.ui.history.HistoryScreen
import com.jadesoft.javhub.ui.home.HomeScreen
import com.jadesoft.javhub.ui.library.LibraryScreen
import com.jadesoft.javhub.ui.more.MoreScreen
import com.jadesoft.javhub.ui.movie.ListType
import com.jadesoft.javhub.ui.movie.MovieScreen
import com.jadesoft.javhub.ui.search.SearchScreen
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
    ) {
//        composable( Destinations.HOME ) { HomeScreen(navController) }

//        composable(
//            route = "${Destinations.DETAIL}/{movieCode}",
//            arguments = listOf(navArgument("movieCode") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val movieCode = backStackEntry.arguments?.getString("movieCode")
//            movieCode.let {
//                if (it != null) {
//                    DetailScreen(code = it, navController = navController)
//                }
//            }
//        }

        composable<Home> { HomeScreen(navController) }

        composable<MovieRoute>{ backStackEntry ->
            val movie: MovieRoute = backStackEntry.toRoute()
            DetailScreen(
                code = movie.code,
                coverUrl = movie.imgUrl,
                title = movie.title,
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

//        composable( Destinations.LIBRARY ) { LibraryScreen(navController) }
//        composable( Destinations.EXPLORE ) { ExploreScreen(navController) }
//        composable( Destinations.HISTORY ) { HistoryScreen(navController) }
        composable( Destinations.CATEGORY ) { CategoryScreen(navController) }
//        composable( Destinations.MORE ) { MoreScreen(navController) }
        composable( Destinations.SEARCH ) { SearchScreen(navController) }
    }
}


object Destinations {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val LIBRARY = "library"
    const val EXPLORE = "explore"
    const val HISTORY = "history"
    const val CATEGORY = "category"
    const val MORE = "more"
    const val SEARCH = "search"
}


@Serializable
object Home

@Serializable
data class MovieRoute(val code: String, val imgUrl: String, val title: String)

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