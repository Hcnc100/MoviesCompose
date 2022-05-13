package com.nullpointer.moviescompose.ui.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.presentation.MoviesViewModel
import com.nullpointer.moviescompose.ui.screens.components.ScrollMovies
import java.lang.reflect.Modifier

@Composable
fun MoviesScreens(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
) {
    val statePopularMovies = moviesViewModel.listPopularMovies.collectAsState()
    val stateTopRatedMovies = moviesViewModel.listTopRated.collectAsState()
    val stateUpComingMovies = moviesViewModel.listUpComingMovies.collectAsState()
    val messageMovies = moviesViewModel.messageMovie

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
        }
    ) {
        LazyColumn {
            item {
                statePopularMovies.value?.let { it1 ->
                    ScrollMovies(titleMovies = "Popular", listMovies = it1)
                }
            }

            item {
                stateTopRatedMovies.value?.let { it1 ->
                    ScrollMovies(titleMovies = "Top Rated", listMovies = it1)
                }
            }
            item {
                stateUpComingMovies.value?.let { it1 ->
                    ScrollMovies(titleMovies = "Up Coming", listMovies = it1)
                }
            }
        }
    }
}