package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.viewmodels.WatchlistViewModel
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(
    navController: NavController,
    viewModel: WatchlistViewModel
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = { SimpleTopAppBar(title = "Your Watchlist") },
        bottomBar = { SimpleBottomAppBar(navController = navController) }
    ) { innerPadding ->
        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = favoriteMovies,
            navController = navController,
            onFavoriteClick = { movieId -> viewModel.toggleFavorite(movieId) }
        )
    }
}