package com.nullpointer.moviescompose.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nullpointer.moviescompose.models.MovieDB

@Composable
fun ScrollMovies(
    titleMovies:String,
    listMovies:List<MovieDB>,
    modifier: Modifier=Modifier
) {
    Column(modifier = modifier) {
        Text(titleMovies)
        Row(modifier = Modifier.fillMaxWidth()) {
            LazyRow{
                items(listMovies.size){index->
                    ItemMovie(movie = listMovies[index])
                }
            }
        }
    }
}