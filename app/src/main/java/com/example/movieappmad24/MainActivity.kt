package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.layout.ContentScale
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.animation.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }

    @Composable
    fun MovieApp() {
        MovieAppMAD24Theme {
            Scaffold(
                topBar = { MovieTopAppBar() },
                bottomBar = { SimpleBottomNavigationBar() }
            ) { innerPadding ->
                MovieCardContent(Modifier.padding(innerPadding))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MovieTopAppBar() {
        CenterAlignedTopAppBar(
            title = { Text("MovieAppMAD24") }
        )
    }

    @Composable
    fun SimpleBottomNavigationBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Handle Home Click */ }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = { /* Handle Watchlist Click */ }) {
                Icon(Icons.Filled.List, contentDescription = "Watchlist")
            }
        }
    }

    @Composable
    fun MoviesList(movies: List<Movie>, modifier: Modifier = Modifier) {
        LazyColumn(modifier = modifier) {
            items(movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }

    @Composable
    fun MovieItem(movie: Movie) {
        // State to manage if the details are shown or not
        var expanded by remember { mutableStateOf(false) }
        // Toggle function for expanded state
        val toggleExpand = { expanded = !expanded }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .animateContentSize() // Animate the content size changes
        ) {
            Card {
                Box {
                    AsyncImage(
                        model = movie.images.firstOrNull(),
                        contentDescription = "Movie Image",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = movie.title)
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Toggle Details",
                    modifier = Modifier.clickable { toggleExpand() }
                )
            }
            // Animated visibility for movie details
            AnimatedVisibility(visible = expanded) {
                Column {
                    Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Year: ${movie.year}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Plot: ${movie.plot}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    @Composable
    fun MovieCardContent(modifier: Modifier = Modifier) {
        val movies = getMovies() // Fetch movies from the getMovies function
        MoviesList(movies = movies, modifier)
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MovieAppMAD24Theme {
            MovieCardContent()
        }
    }
}
