package com.example.movieappmad24.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.movieappmad24.models.Movie

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Movie::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["movieId"])] // Ensure an index on foreign key column
)
data class MovieImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: String,
    val url: String
)