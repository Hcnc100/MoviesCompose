package com.nullpointer.moviescompose.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.models.TypeMovie
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import com.nullpointer.moviescompose.presentation.SearchViewModel
import com.nullpointer.moviescompose.ui.screens.animation.AnimationScreen
import com.nullpointer.moviescompose.ui.screens.destinations.DetailsMovieScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val stateSearch = searchViewModel.listSearch.collectAsState()
    val messageSearch = searchViewModel.messageSearch
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(key1 = Unit) {
        messageSearch.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(it)
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SearchToolbar(
                queryInput = searchViewModel.querySearch,
                focusRequester = focusRequester,
                changeQuery = searchViewModel::searchMovie,
                actionClear = searchViewModel::clearSearch
            )
        }
    ) { paddingValues ->
        val listMovies = stateSearch.value
        when {
            searchViewModel.isLoading -> {
                AnimationScreen(animation = R.raw.search, textEmpty = "Buscando...")
            }
            searchViewModel.querySearch.isEmpty() -> {
                AnimationScreen(animation = R.raw.movie,  textEmpty = "Busca tu pelicula")
            }
            searchViewModel.querySearch.isNotEmpty() && listMovies.isEmpty()->{
                AnimationScreen(animation = R.raw.no_found,  textEmpty = "No se encontraron resultados")
            }
            searchViewModel.querySearch.isNotEmpty() && listMovies.isNotEmpty()->{
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(listMovies.size) { index ->
                        ItemSearch(movie = listMovies[index], actionClick = {
                            navigator.navigate(DetailsMovieScreenDestination.invoke(
                                MovieDB.fromMovieApi(it, TypeMovie.TOP_RATED)
                            ))
                        })
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemSearch(
    movie: MovieApiResponse.Movie,
    actionClick: (MovieApiResponse.Movie) -> Unit,
) {
    val painter = rememberImagePainter(data = movie.poster_path) {
        error(R.drawable.ic_broken_image)
        placeholder(R.drawable.ic_movies)
        crossfade(true)
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp, horizontal = 4.dp), onClick = { actionClick(movie) }) {
        Row(Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painter,
                contentDescription = "Imagen de portada de la pelicula",
                modifier = Modifier
                    .height(50.dp)
                    .width(30.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = movie.title, style = MaterialTheme.typography.body1, maxLines = 1)
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchToolbar(
    queryInput: String,
    actionClear: () -> Unit,
    changeQuery: (query: String) -> Unit,
    focusRequester: FocusRequester,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = queryInput,
        onValueChange = changeQuery,
        trailingIcon = {
            FloatingActionButton(onClick = actionClear, modifier = Modifier.size(20.dp)) {
                Icon(modifier = Modifier.padding(4.dp),
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = "Icono de limpieza")
            }
        },
        placeholder = { Text(text = "Escribe el texto a buscar") },
        label = { Text("Busqueda") },
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Icono de busqueda")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { keyboardController?.hide() }
        ),
    )
}