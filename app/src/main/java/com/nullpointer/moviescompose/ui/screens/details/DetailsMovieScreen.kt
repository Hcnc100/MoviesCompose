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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.nullpointer.moviescompose.ui.screens.movies.components.FakeListScroll
import com.nullpointer.moviescompose.ui.states.DetailsScreenState
import com.nullpointer.moviescompose.ui.states.rememberDetailsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy


@Destination
@Composable
fun DetailsMovieScreen(
    movie: MovieDB,
    navigator: DestinationsNavigator,
    castViewModel: CastViewModel = shareViewModel(),
    detailsStateScreen: DetailsScreenState = rememberDetailsScreenState()
) {

    val stateListCast by castViewModel.listCastMovie.collectAsState()
    val maxTextLinesTitle by remember(detailsStateScreen.progressCollapsing) {
        derivedStateOf {
            if (detailsStateScreen.progressCollapsing < 0.8f) 1 else Int.MAX_VALUE
        }
    }

    LaunchedEffect(key1 = Unit) {
        castViewModel.messageCast.collect(detailsStateScreen::showSnackMessage)
    }

    Scaffold(
        scaffoldState = detailsStateScreen.scaffoldState
    ) {
        CollapsingToolbarScaffold(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            state = detailsStateScreen.toolbarState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                HeaderMovie(
                    movie = movie,
                    modifier = Modifier.parallax(),
                    paddingHeader = detailsStateScreen.innerPadding,
                    alphaBackground = detailsStateScreen.progressCollapsing
                )

                IconBack(
                    actionClickBack = navigator::popBackStack,
                    modifier = Modifier.size(detailsStateScreen.paddingIconNav.dp)
                )

                TitleRoad(
                    title = movie.title,
                    maxLinesTitle = maxTextLinesTitle,
                    fontSize = detailsStateScreen.textSizeTitle,
                    heightMinToolbar = detailsStateScreen.heightMinToolbar,
                    modifier = Modifier
                        .road(Alignment.TopStart, Alignment.TopCenter)
                        .padding(
                            start = detailsStateScreen.paddingStartTitle.dp,
                            end = detailsStateScreen.innerPadding.dp,
                            top = detailsStateScreen.paddingTopTitle.dp,
                        )
                )
            }) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                ReviewMovie(movie = movie)
                ListCastState(listCast = stateListCast)
            }

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
        else -> FakeListScroll()
    }
}


@Composable
private fun InfoCardMovie(
    movieDB: MovieDB,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {

    val dateMovie = remember {
        movieDB.releaseDate?.convertTime() ?: context.getString(R.string.sub_title_time_movie)
    }
    val voteAverage = remember { (movieDB.voteAverage * 10).toInt() }


    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = movieDB.originalTitle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.W500,
                color = Color.White,
                fontSize = 14.sp
            ),
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Icon(
                tint = Color.White,
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_start),
                contentDescription = stringResource(R.string.description_value)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                color = Color.White,
                style = MaterialTheme.typography.caption,
                text = stringResource(R.string.sub_title_value, voteAverage),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = stringResource(R.string.description_clock_icon),
                tint = Color.White, modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = dateMovie,
                color = Color.White,
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Composable
private fun HeaderMovie(
    movie: MovieDB,
    paddingHeader: Float,
    alphaBackground: Float,
    modifier: Modifier = Modifier,
    colorBackground: Color = MaterialTheme.colors.primary,
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
        modifier = modifier
            .background(colorBackground)
            .fillMaxWidth()
            .height(310.dp)
    ) {
        Image(
            modifier = Modifier
                .alpha(alphaBackground)
                .fillMaxSize(),
            painter = painterPoster,
            contentDescription = stringResource(R.string.description_background_img),
            contentScale = if (painterPoster.state is AsyncImagePainter.State.Success) ContentScale.Crop else ContentScale.Fit
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .3f))
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp),
                painter = painterMovie,
                contentDescription = stringResource(id = R.string.description_img_movie),
                contentScale = if (painterPoster.state is AsyncImagePainter.State.Success) ContentScale.Crop else ContentScale.Fit
            )
        }

        InfoCardMovie(
            movieDB = movie, modifier = Modifier
                .padding(paddingHeader.dp)
                .align(Alignment.BottomEnd)
                .clip(MaterialTheme.shapes.small)
                .background(Color.Gray.copy(alpha = 0.3f))
                .width(150.dp)
        )

    }
}

@Composable
fun TitleRoad(
    title: String,
    maxLinesTitle: Int,
    fontSize: Float,
    heightMinToolbar: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(heightMinToolbar.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            modifier = Modifier,
            color = Color.White,
            fontSize = fontSize.sp,
            maxLines = maxLinesTitle,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun IconBack(
    actionClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = actionClickBack,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null
        )
    }
}