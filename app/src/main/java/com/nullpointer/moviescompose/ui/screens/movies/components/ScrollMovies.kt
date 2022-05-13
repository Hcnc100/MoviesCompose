package com.nullpointer.moviescompose.ui.screens.movies.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.moviescompose.models.MovieDB
import com.valentinilk.shimmer.shimmer

@Composable
fun ScrollMovies(
    titleMovies: String,
    listMovies: List<MovieDB>?,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    actionClick: (MovieDB) -> Unit,
) {
    if (isLoading) {
        ScrollMoviesFake(sizeFake = 3)
    } else {
        Column(modifier = modifier) {
            Text(titleMovies,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                fontSize = 18.sp
            )
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(listMovies!!.size) { index ->
                    ItemMovie(
                        movie = listMovies[index],
                        actionClick = actionClick)
                }
            }
        }
    }
}

@Composable
fun ScrollMoviesFake(
    sizeFake: Int,
) {
    Column {
        Card(modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .width(120.dp)
            .height(20.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Gray.copy(alpha = .5f))
            .shimmer()) {}
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(sizeFake) {
                Card(modifier = Modifier
                    .height(200.dp)
                    .width(150.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray.copy(alpha = .5f))
                    .shimmer()) {}
            }

        }
    }
}