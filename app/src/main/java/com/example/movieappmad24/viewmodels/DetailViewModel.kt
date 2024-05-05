package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository, movieId: String) : ViewModel() {
    val movie: StateFlow<Movie?> = repository.getById(movieId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun toggleFavoriteMovie(movieId: String) {
        viewModelScope.launch {
            val movie = repository.getById(movieId).firstOrNull()
            movie?.let {
                it.isFavorite = !it.isFavorite
                repository.updateMovie(it)
            }
        }
    }

    fun getMovieById(movieId: String): StateFlow<Movie?> {
        return repository.getById(movieId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    }
}