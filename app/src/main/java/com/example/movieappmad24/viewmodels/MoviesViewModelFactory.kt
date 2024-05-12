package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepository

class MoviesViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(WatchlistViewModel::class.java) -> WatchlistViewModel(repository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                val movieId = "DEFAULT VALUE"
                DetailViewModel(repository, movieId) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
