package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class WatchlistViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect { movies ->
                _favoriteMovies.value = movies
            }
        }
    }

    fun toggleFavorite(movieId: String) {
        viewModelScope.launch {
            val movie = repository.getById(movieId).firstOrNull()
            movie?.let {
                it.isFavorite = !it.isFavorite
                repository.updateMovie(it)
                updateFavorites() // Update the list of favorite movies after toggling
            }
        }
    }

    private suspend fun updateFavorites() {
        repository.getFavoriteMovies().collect { movies ->
            _favoriteMovies.value = movies
        }
    }
}
