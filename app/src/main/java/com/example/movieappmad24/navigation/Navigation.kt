package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.DetailViewModel
import com.example.movieappmad24.viewmodels.HomeViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory
import com.example.movieappmad24.viewmodels.WatchlistViewModel
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository

@Composable
fun Navigation() {
    val navController = rememberNavController() // Create a NavController instance

    // Initialize database and repository
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = MovieDatabase.getDatabase(context, scope)
    val repository = MovieRepository(movieDao = db.movieDao())

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        // Home screen
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }

        // Detail screen with passing movieId as a parameter
        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) { type = NavType.StringType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY)
            val detailViewModel: DetailViewModel = viewModel(factory = MoviesViewModelFactory(repository))
            DetailScreen(navController = navController, viewModel = detailViewModel, movieId = movieId)
        }

        // Watchlist screen
        composable(route = Screen.WatchlistScreen.route) {
            val watchlistViewModel: WatchlistViewModel = viewModel(factory = MoviesViewModelFactory(repository))
            WatchlistScreen(viewModel = watchlistViewModel, navController =  navController)
        }
    }
}
