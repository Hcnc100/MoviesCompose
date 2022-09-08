package com.nullpointer.moviescompose.ui.screens.details

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.Resource
import com.nullpointer.moviescompose.core.utils.convertTime
import com.nullpointer.moviescompose.core.utils.shareViewModel
import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.presentation.CastViewModel
import com.nullpointer.moviescompose.ui.screens.details.componets.ItemCast
import com.nullpointer.moviescompose.ui.screens.movies.components.CardContainerFake
import com.nullpointer.moviescompose.ui.screens.movies.components.TitleFakeMovie
import com.nullpointer.moviescompose.ui.share.ToolbarBack
import com.nullpointer.moviescompose.ui.states.SimpleScreenState
import com.nullpointer.moviescompose.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsMovieScreen(
    movie: MovieDB,
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel = shareViewModel(),
    detailsStateScreen: SimpleScreenState = rememberSimpleScreenState()
) {
    val stateListCast by castViewModel.listCastMovie.collectAsState()

    LaunchedEffect(key1 = Unit) {
        castViewModel.messageCast.collect(detailsStateScreen::showSnackMessage)
    }
    Scaffold(
        scaffoldState = detailsStateScreen.scaffoldState,
        topBar = {
            ToolbarBack(
                title = stringResource(id = R.string.title_details_movie),
                actionBack = navigator::popBackStack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderMovie(movie = movie)
            ReviewMovie(movie = movie)
            ListCastState(listCast = stateListCast)
        }
    }
}

@Composable
private fun ReviewMovie(movie: MovieDB) {
    Card(modifier = Modifier.padding(10.dp), shape = RoundedCornerShape(5.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(R.string.title_overview),
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = movie.description, style = MaterialTheme.typography.body1)
        }
    }
}

@Composable
private fun ListCastState(
    listCast: Resource<List<Cast>>
) {
    when (listCast) {
        is Resource.Success -> {
            Text(
                text = stringResource(R.string.title_casting),
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                style = MaterialTheme.typography.h6
            )
            LazyRow {
                items(listCast.data,
                    key = { it.name }) { cast ->
                    ItemCast(cast)
                }
            }
        }
        else -> {
            Column {
                TitleFakeMovie()
                LazyRow {
                    items(5, key = { it }) {
                        CardContainerFake()
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderMovie(
    movie: MovieDB,
) {
    val painterMovie = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .crossfade(true)
            .data(movie.imgMovie)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_movies),
        error = painterResource(id = R.drawable.ic_broken_image)
    )
    val painterPoster = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .crossfade(true)
            .data(movie.imgCover)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_movies),
        error = painterResource(id = R.drawable.ic_broken_image)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterPoster,
            contentDescription = stringResource(R.string.description_background_img),
            modifier = Modifier.fillMaxSize(),
            contentScale = if (painterPoster.state is AsyncImagePainter.State.Success) ContentScale.Crop else ContentScale.Fit
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .3f))
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter),
        ) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Image(
                    painter = painterMovie,
                    contentDescription = stringResource(id = R.string.description_img_movie),
                    contentScale = if (painterPoster.state is AsyncImagePainter.State.Success) ContentScale.Crop else ContentScale.Fit,
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
private fun InfoCardMovie(
    movieDB: MovieDB,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    val dateMovie by remember {
        derivedStateOf {
            movieDB.releaseDate?.convertTime() ?: context.getString(R.string.sub_title_time_movie)
        }
    }
    val voteAverage by remember {
        derivedStateOf {
            "${(movieDB.voteAverage * 10).toInt()} %"
        }
    }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = movieDB.title,
            style = MaterialTheme.typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = movieDB.originalTitle,
            style = MaterialTheme.typography.caption,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.W500,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_start),
                contentDescription = stringResource(R.string.description_value),
                tint = Color.White, modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = stringResource(R.string.sub_title_value),
                style = MaterialTheme.typography.caption,
                color = Color.White)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = voteAverage,
                style = MaterialTheme.typography.caption, color = Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_time),
                contentDescription = stringResource(R.string.description_clock_icon),
                tint = Color.White, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = dateMovie,
                style = MaterialTheme.typography.caption, color = Color.White)
        }
    }
}