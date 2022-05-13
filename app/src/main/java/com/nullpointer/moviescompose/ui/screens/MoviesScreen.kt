package com.nullpointer.moviescompose.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.presentation.MoviesViewModel
import com.nullpointer.moviescompose.ui.screens.components.ScrollMovies
import com.nullpointer.moviescompose.ui.screens.components.ScrollMoviesFake

@Composable
fun MoviesScreens(
    moviesViewModel: MoviesViewModel = hiltViewModel(),
) {
    val statePopularMovies = moviesViewModel.listPopularMovies.collectAsState()
    val stateTopRatedMovies = moviesViewModel.listTopRated.collectAsState()
    val stateUpComingMovies = moviesViewModel.listUpComingMovies.collectAsState()
    val isLoadingMovies = moviesViewModel.isLoading.collectAsState(initial = true)
    val messageMovies = moviesViewModel.messageMovie
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        messageMovies.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(it)
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.app_name)) })
        }
    ) {
        val isLoading = isLoadingMovies.value
        SwipeRefresh(state = rememberSwipeRefreshState(moviesViewModel.isRequested),
            onRefresh = moviesViewModel::updateAllMovies) {
            LazyColumn(modifier = Modifier.padding(it)) {
                item {
                    if (isLoading) {
                        ScrollMoviesFake(sizeFake = 3)
                    } else {
                        ScrollMovies(
                            listMovies = statePopularMovies.value!!,
                            titleMovies = stringResource(R.string.title_popular),
                        )
                    }
                }
                item {
                    if (isLoading) {
                        ScrollMoviesFake(sizeFake = 3)
                    } else {
                        ScrollMovies(
                            listMovies = stateTopRatedMovies.value!!,
                            titleMovies = stringResource(R.string.title_top_rated))
                    }
                }
                item {
                    if (isLoading) {
                        ScrollMoviesFake(sizeFake = 3)
                    } else {
                        ScrollMovies(
                            listMovies = stateUpComingMovies.value!!,
                            titleMovies = stringResource(R.string.title_up_comming))
                    }
                }
            }
        }
    }
}