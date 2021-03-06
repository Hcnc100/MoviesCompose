package com.nullpointer.moviescompose.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.convertTime
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.presentation.CastViewModel
import com.nullpointer.moviescompose.ui.screens.details.componets.ItemCast
import com.nullpointer.moviescompose.ui.screens.movies.components.ScrollMoviesFake
import com.nullpointer.moviescompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsMovieScreen(
    movie: MovieDB,
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel,
) {

    val scaffoldState = rememberScaffoldState()
    val messageCast = castViewModel.messageCast
    val context = LocalContext.current
    val stateListCast = castViewModel.listCastMovie.collectAsState()

    LaunchedEffect(key1 = Unit) {
        castViewModel.getCastFromMovie(movie.id!!)
    }
    LaunchedEffect(key1 = Unit) {
        messageCast.collect {
            scaffoldState.snackbarHostState.showSnackbar(context.getString(it))
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ToolbarBack(title = stringResource(id = R.string.title_details_movie),
                actionBack = navigator::popBackStack)
        }
    ) {
        val listCast = stateListCast.value

        Column(modifier = Modifier
            .padding(it)
            .verticalScroll(rememberScrollState())) {
            HeaderMovie(movie = movie)
            Card(modifier = Modifier.padding(10.dp), shape = RoundedCornerShape(5.dp)) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = stringResource(R.string.title_overview),
                        style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = movie.description, style = MaterialTheme.typography.body1)
                }
            }
            if (castViewModel.isRequestedCast) {
                ScrollMoviesFake(3)
            } else {
                Text(text = stringResource(R.string.title_casting),
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                    style = MaterialTheme.typography.h6)
                LazyRow {
                    items(listCast.size) { index ->
                        ItemCast(listCast[index])
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderMovie(
    movie: MovieDB,
) {
    val painterMovie = rememberImagePainter(movie.imgMovie) {
        error(R.drawable.ic_broken_image)
        placeholder(R.drawable.ic_movies)
        crossfade(true)
    }
    val painterCover = rememberImagePainter(movie.imgCover) {
        error(R.drawable.ic_broken_image)
        placeholder(R.drawable.ic_movies)
        crossfade(true)
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(250.dp)) {
        Image(painter = painterCover,
            contentDescription = stringResource(R.string.description_background_img),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = .3f)))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter),
        ) {
            Card(modifier = Modifier
                .fillMaxHeight()
                .width(100.dp),
                shape = RoundedCornerShape(10.dp)) {
                Image(painter = painterMovie,
                    contentDescription = stringResource(id = R.string.description_img_movie),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            InfoCardMovie(
                movieDB = movie,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp)
            )
        }
    }
}

@Composable
fun InfoCardMovie(movieDB: MovieDB, modifier: Modifier = Modifier) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.Center) {
        Text(text = movieDB.title,
            style = MaterialTheme.typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = movieDB.originalTitle,
            style = MaterialTheme.typography.caption,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.W500,
            color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_start),
                contentDescription = stringResource(R.string.description_value),
                tint = Color.White, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = stringResource(R.string.sub_title_value),
                style = MaterialTheme.typography.caption,
                color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "${(movieDB.voteAverage * 10).toInt()} %",
                style = MaterialTheme.typography.caption, color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_time),
                contentDescription = stringResource(R.string.description_clock_icon),
                tint = Color.White, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = movieDB.releaseDate?.convertTime()
                ?: stringResource(R.string.sub_title_time_movie),
                style = MaterialTheme.typography.caption, color = Color.White)
        }
    }
}