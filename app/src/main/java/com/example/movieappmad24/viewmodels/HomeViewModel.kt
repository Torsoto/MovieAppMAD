package com.example.movieappmad24.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieImage
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    val movies = repository.getAllMovies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        val movies = getMovies()
        viewModelScope.launch {
            if(repository.countMovies() == 0){
                var i = 1
                movies.forEach { movie: Movie ->
                    repository.addMovie(movie)
                    movie.images.forEach {
                        image -> repository.addMovieImage(movieImage = MovieImage(movieId = i.toLong(),
                            url = image))

                    }
                    i++
                }

            }
        }
    }

    fun toggleFavoriteMovie(movieId: String) {
        viewModelScope.launch {
            val movie = repository.getById(movieId).firstOrNull()
            movie?.let {
                it.isFavorite = !it.isFavorite
                repository.updateMovie(it)
            }
        }
    }

    fun addSampleData() {
        viewModelScope.launch {
            val movieExists = repository.checkMovieExists("tt0111161") // Movie ID to check
            if (!movieExists) {
                val sampleMovie = Movie(
                    id = "tt0111161",  // Example ID
                    title = "The Shawshank Redemption",
                    year = "1994",
                    genre = "Drama",
                    director = "Frank Darabont",
                    actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
                    plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                    trailer = "sample_trailer",
                    rating = "9.3",
                    isFavorite = false
                )
                repository.addMovie(sampleMovie)

            } else {
                Log.d("ViewModel", "Movie already exists in the database.")
            }
        }
    }
}