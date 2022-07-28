package com.nullpointer.moviescompose.ui.screens.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.shareViewModel
import com.nullpointer.moviescompose.presentation.CastViewModel
import com.nullpointer.moviescompose.presentation.MoviesViewModel
import com.nullpointer.moviescompose.ui.screens.destinations.DetailsMovieScreenDestination
import com.nullpointer.moviescompose.ui.screens.destinations.SearchScreenDestination
import com.nullpointer.moviescompose.ui.screens.movies.components.ScrollMovies
import com.nullpointer.moviescompose.ui.share.ToolbarBack
import com.nullpointer.moviescompose.ui.states.MoviesScreenState
import com.nullpointer.moviescompose.ui.states.rememberMoviesScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun MoviesScreens(
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel= shareViewModel(),
    moviesViewModel: MoviesViewModel = shareViewModel(),
    moviesScreenState: MoviesScreenState = rememberMoviesScreenState()
) {
    val statePopularMovies by moviesViewModel.listPopularMovies.collectAsState()
    val stateTopRatedMovies by moviesViewModel.listTopRated.collectAsState()
    val stateUpComingMovies by moviesViewModel.listUpComingMovies.collectAsState()


    LaunchedEffect(key1 = Unit) {
        moviesViewModel.messageMovie.collect(moviesScreenState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = moviesScreenState.scaffoldState,
        topBar = {
            ToolbarBack(
                title = stringResource(id = R.string.app_name),
                actionSearch = { navigator.navigate(SearchScreenDestination) }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(moviesViewModel.isRequested),
            onRefresh = moviesViewModel::updateAllMovies) {
            Column(modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())) {
                ScrollMovies(
                    listMovies = statePopularMovies,
                    titleMovies = stringResource(R.string.title_popular),
                    actionClick = {
                        navigator.navigate(DetailsMovieScreenDestination.invoke(it))
                        castViewModel.getCastFromMovie(it.id)
                    }
                )
                ScrollMovies(
                    listMovies = stateTopRatedMovies,
                    titleMovies = stringResource(R.string.title_top_rated),
                    actionClick = {
                        navigator.navigate(DetailsMovieScreenDestination.invoke(it))
                        castViewModel.getCastFromMovie(it.id)
                    })
                ScrollMovies(
                    listMovies = stateUpComingMovies,
                    titleMovies = stringResource(R.string.title_up_comming),
                    actionClick = {
                        navigator.navigate(DetailsMovieScreenDestination.invoke(it))
                        castViewModel.getCastFromMovie(it.id)
                    }
                )
            }
        }
    }
}
