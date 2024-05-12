package com.example.movieappmad24.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movieappmad24.models.Movie

@Entity(tableName = "movieImage")
data class MovieImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: Long,
    val url: String
)