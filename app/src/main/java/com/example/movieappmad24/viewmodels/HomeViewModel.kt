package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    val movies = repository.getAllMovies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleFavoriteMovie(movieId: String) {
        viewModelScope.launch {
            val movie = repository.getById(movieId).firstOrNull()
            movie?.let {
                it.isFavorite = !it.isFavorite
                repository.updateMovie(it)
            }
        }
    }
}