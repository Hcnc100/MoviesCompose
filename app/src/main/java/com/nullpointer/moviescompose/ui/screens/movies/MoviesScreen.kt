package com.nullpointer.moviescompose.ui.screens.movies

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.presentation.MoviesViewModel
import com.nullpointer.moviescompose.ui.screens.destinations.DetailsMovieScreenDestination
import com.nullpointer.moviescompose.ui.screens.movies.components.ScrollMovies
import com.nullpointer.moviescompose.ui.screens.movies.components.ScrollMoviesFake
import com.nullpointer.moviescompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun MoviesScreens(
    moviesViewModel: MoviesViewModel,
    navigator: DestinationsNavigator,
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
            ToolbarBack(title = stringResource(id = R.string.app_name))
        }
    ) {
        val isLoading = isLoadingMovies.value
        SwipeRefresh(state = rememberSwipeRefreshState(moviesViewModel.isRequested),
            onRefresh = moviesViewModel::updateAllMovies) {
            Column(modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
                ScrollMovies(
                    listMovies = statePopularMovies.value,
                    titleMovies = stringResource(R.string.title_popular),
                    isLoading = isLoading,
                    actionClick = {
                        navigator.navigate(DetailsMovieScreenDestination.invoke(it))
                    }
                )
                ScrollMovies(
                    listMovies = stateTopRatedMovies.value,
                    titleMovies = stringResource(R.string.title_top_rated),
                    isLoading = isLoading,
                    actionClick = {
                        navigator.navigate(DetailsMovieScreenDestination.invoke(it))
                    })
                ScrollMovies(
                    listMovies = stateUpComingMovies.value,
                    titleMovies = stringResource(R.string.title_up_comming),
                    isLoading = isLoading,
                    actionClick = {
                        navigator.navigate(DetailsMovieScreenDestination.invoke(it))
                    }
                )
            }
        }
    }
}
