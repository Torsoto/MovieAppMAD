package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleBottomAppBar
import com.example.movieappmad24.widgets.SimpleTopAppBar
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.movieappmad24.viewmodels.HomeViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = remember(lifecycleOwner) { lifecycleOwner.lifecycleScope }

    val db = MovieDatabase.getDatabase(context, scope)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MoviesViewModelFactory(repository = repository)
    val viewModel: HomeViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Movie App")
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)
        }
    ) { innerPadding ->
        val moviesState by viewModel.movies.collectAsState()
        MovieList(
            modifier = Modifier.padding(innerPadding),
            movies = moviesState,
            navController = navController,
            onFavoriteClick = { movieId -> viewModel.toggleFavoriteMovie(movieId) }
        )
    }
}