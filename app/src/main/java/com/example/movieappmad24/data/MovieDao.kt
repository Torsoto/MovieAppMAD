package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.data.MovieImage
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun add(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Insert
    suspend fun addMovieImage(movieImage: MovieImage)

    @Query("SELECT * FROM movie WHERE Id=:id")
    fun get(id: String): Flow<Movie>

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Movie>>

    @Insert
    suspend fun addImage(movieImage: MovieImage)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM movie WHERE movie.dbId = :id")
    fun getById(id: Long?) : Flow<Movie>

    @Transaction
    @Query("SELECT * FROM movie")
    fun getAllMoviesWithImages(): Flow<List<MovieWithImages>>

    @Transaction
    @Query("SELECT * FROM movie WHERE dbId = :id")
    fun getMovieWithImages(id: Long): Flow<MovieWithImages>

    @Query("SELECT * FROM movie WHERE id = :id LIMIT 1")
    suspend fun getMovieById(id: String): Movie?

    @Query("SELECT COUNT(*) FROM movie")
    suspend fun countMovies() : Int
}
