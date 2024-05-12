package com.example.movieappmad24.data

import androidx.room.Embedded
import androidx.room.Relation
import com.example.movieappmad24.models.Movie

data class MovieWithImages(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val images: List<MovieImage>
)