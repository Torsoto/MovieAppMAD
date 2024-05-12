package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    suspend fun addMovie(movie: Movie) = movieDao.add(movie)

    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)

    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    suspend fun deleteAllMovies() = movieDao.deleteAll()


    fun getAllMovies(): Flow<List<Movie>> = movieDao.getAll()

    fun getFavoriteMovies(): Flow<List<Movie>> = movieDao.getFavorites()

    fun getById(id: String): Flow<Movie?> = movieDao.get(id)

    suspend fun checkMovieExists(id: String): Boolean {
        return movieDao.getMovieById(id) != null
    }

    suspend fun countMovies() = movieDao.countMovies()
    fun getAllMoviesWithImages(): Flow<List<MovieWithImages>> = movieDao.getAllMoviesWithImages()

    suspend fun addMovieImage(movieImage: MovieImage) = movieDao.addMovieImage(movieImage)
}
