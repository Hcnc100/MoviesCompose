package com.nullpointer.moviescompose.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.Resource
import com.nullpointer.moviescompose.core.utils.shareViewModel
import com.nullpointer.moviescompose.data.remote.datasource.toMovie
import com.nullpointer.moviescompose.models.TypeMovie
import com.nullpointer.moviescompose.models.apiResponse.movie.MovieDTO
import com.nullpointer.moviescompose.presentation.CastViewModel
import com.nullpointer.moviescompose.presentation.SearchViewModel
import com.nullpointer.moviescompose.ui.screens.animation.AnimationScreen
import com.nullpointer.moviescompose.ui.screens.destinations.DetailsMovieScreenDestination
import com.nullpointer.moviescompose.ui.share.AsyncImageFade
import com.nullpointer.moviescompose.ui.states.SearchScreenState
import com.nullpointer.moviescompose.ui.states.rememberSearchScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel = shareViewModel(),
    searchViewModel: SearchViewModel = hiltViewModel(),
    searchScreenState: SearchScreenState = rememberSearchScreenState()
) {
    val listMovies by searchViewModel.listSearch.collectAsState()

    LaunchedEffect(key1 = Unit) {
        searchScreenState.requestFocus()
        searchViewModel.messageSearch.collect(searchScreenState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = searchScreenState.scaffoldState,
        topBar = {
            SearchToolbar(
                queryInput = searchViewModel.querySearch,
                changeQuery = searchViewModel::searchMovie,
                focusRequester = searchScreenState.focusRequester,
                hiddenKeyboard = searchScreenState::hiddenKeyboard
            )
        }
    ) { paddingValues ->
        ListSearchMovies(
            modifier = Modifier.padding(paddingValues),
            stateListSearch = listMovies,
            actionClickMovie = {
                castViewModel.getCastFromMovie(it.id)
                navigator.navigate(DetailsMovieScreenDestination(it.toMovie(TypeMovie.TOP_RATED)))
            })
    }
}

@Composable
private fun ListSearchMovies(
    stateListSearch: Resource<List<MovieDTO>>?,
    modifier: Modifier = Modifier,
    actionClickMovie: (MovieDTO) -> Unit
) {
    when (stateListSearch) {
        Resource.Failure -> {
            AnimationScreen(
                animation = R.raw.no_found,
                textEmpty = stringResource(R.string.error_search_movie),
                modifier = modifier
            )
        }
        Resource.Loading -> {
            AnimationScreen(
                animation = R.raw.search,
                textEmpty = stringResource(R.string.message_search_movie),
                modifier = modifier
            )
        }
        is Resource.Success -> {
            val listMovies = stateListSearch.data
            if (listMovies.isEmpty()) {
                AnimationScreen(
                    animation = R.raw.no_found,
                    textEmpty = stringResource(R.string.message_no_found_screen),
                    modifier = modifier
                )
            } else {
                LazyColumn(modifier = modifier) {
                    items(listMovies, key = { it.id }) { movie ->
                        ItemSearch(
                            movie = movie,
                            actionClick = actionClickMovie
                        )
                    }
                }
            }
        }
        else -> {
            AnimationScreen(
                animation = R.raw.movie,
                textEmpty = stringResource(R.string.message_start_search_movie),
                modifier = modifier
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemSearch(
    movie: MovieDTO,
    actionClick: (MovieDTO) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 4.dp), onClick = { actionClick(movie) }) {
        Row(Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImageFade(
                data = movie.posterPath,
                contentDescription = R.string.description_img_movie,
                resourceLoading = R.drawable.ic_movies,
                resourceFailed = R.drawable.ic_broken_image,
                modifier = Modifier
                    .height(50.dp)
                    .width(30.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = movie.title, style = MaterialTheme.typography.body1, maxLines = 1)
        }
    }
}


@Composable
private fun SearchToolbar(
    queryInput: String,
    hiddenKeyboard: () -> Unit,
    focusRequester: FocusRequester,
    changeQuery: (query: String) -> Unit
) {

    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = queryInput,
                selection = TextRange(queryInput.length)
            )
        )
    }
    OutlinedTextField(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = textFieldValueState,
        onValueChange = {
            if (it.text != queryInput) changeQuery(it.text)
            textFieldValueState = it
        },
        trailingIcon = {
            if (queryInput.isNotEmpty()) {
                FloatingActionButton(onClick = {
                    textFieldValueState = textFieldValueState.copy(text = "")
                }, modifier = Modifier.size(20.dp)) {
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = stringResource(R.string.description_clear_icon_search)
                    )
                }
            }
        },
        placeholder = { Text(text = stringResource(R.string.placeholder_search_movie)) },
        label = { Text(stringResource(R.string.title_search_movie)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(R.string.description_search_icon)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { hiddenKeyboard() }),
    )
}