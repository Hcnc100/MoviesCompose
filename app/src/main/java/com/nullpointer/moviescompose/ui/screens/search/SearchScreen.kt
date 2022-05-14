package com.nullpointer.moviescompose.ui.screens.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.models.apiResponse.MovieApiResponse
import com.nullpointer.moviescompose.presentation.SearchViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
) {
    val stateSearch = searchViewModel.listSearch.collectAsState()
    val messageSearch = searchViewModel.messageSearch
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

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
                changeQuery = searchViewModel::searchMovie,
                actionClear = searchViewModel::clearSearch
                )
        }
    ) {
        val listMovies = stateSearch.value
        LazyColumn(modifier = Modifier.padding(it)) {
            items(listMovies.size) { index ->
                ItemSearch(movie = listMovies[index])
            }
        }
    }
}

@Composable
fun ItemSearch(
    movie: MovieApiResponse.Movie,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 2.dp, horizontal = 4.dp)) {
        Row(Modifier.padding(4.dp)) {
            Text(text = movie.title)
        }
    }
}


@Composable
fun SearchToolbar(
    queryInput: String,
    actionClear:()->Unit,
    changeQuery: (query: String) -> Unit,
) {
    OutlinedTextField(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        value = queryInput,
        onValueChange = changeQuery,
        trailingIcon = {
            FloatingActionButton(onClick = actionClear, modifier = Modifier.size(20.dp)) {
                Icon(modifier = Modifier.padding(4.dp),painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = "Icono de limpieza")
            }
        },
        placeholder = { Text(text = "Escribe el texto a buscar")},
        label = {Text("Busqueda")},
        maxLines = 1,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Icono de busqueda")
        }
    )
}