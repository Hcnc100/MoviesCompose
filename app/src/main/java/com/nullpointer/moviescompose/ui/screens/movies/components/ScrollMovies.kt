package com.nullpointer.moviescompose.ui.screens.movies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.moviescompose.core.utils.Resource
import com.nullpointer.moviescompose.models.MovieDB

@Composable
fun ScrollMovies(
    titleMovies: String,
    modifier: Modifier = Modifier,
    actionClick: (MovieDB) -> Unit,
    listMovies: Resource<List<MovieDB>>,
) {
    when (listMovies) {
        is Resource.Success ->{
            Column(modifier = modifier){
                Text(
                    text = titleMovies,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    fontSize = 18.sp
                )
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(listMovies.data, key = { it.id }) { movie ->
                        ItemMovie(
                            movie = movie,
                            actionClick = actionClick
                        )
                    }
                }
            }
        }


        else -> FakeListScroll()
    }
}

