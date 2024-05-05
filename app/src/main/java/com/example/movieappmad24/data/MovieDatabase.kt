package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.data.MovieImage

@Database(entities = [Movie::class, MovieImage::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    .addCallback(MovieDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class MovieDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.movieDao())
                }
            }
        }

        suspend fun populateDatabase(movieDao: MovieDao) {
            // Clear all data to start fresh
            movieDao.deleteAll()

            // Add sample movies.
            val movie = Movie(id = "tt0499549", title = "Avatar", year = "2009", genre = "Action, Adventure, Fantasy", director = "James Cameron", actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang", plot = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.", trailer = "trailer_placeholder", rating = "7.9", isFavorite = true)
            movieDao.add(movie)

            // Add images related to 'Avatar'.
            val image1 = MovieImage(id = 1, movieId = movie.id, url = "https://example.com/avatar1.jpg")
            val image2 = MovieImage(id = 2, movieId = movie.id, url = "https://example.com/avatar2.jpg")
            movieDao.addImage(image1)
            movieDao.addImage(image2)

            // Add more movies and images as needed.
        }
    }
}