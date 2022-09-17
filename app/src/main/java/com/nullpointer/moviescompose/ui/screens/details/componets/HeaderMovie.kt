package com.nullpointer.moviescompose.ui.screens.details.componets

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.nullpointer.moviescompose.core.utils.convertTime
import com.nullpointer.moviescompose.models.MovieDB

@Composable
fun HeaderMovie(
    movie: MovieDB,
    paddingHeader: Float,
    alphaBackground: Float,
    modifier: Modifier = Modifier,
    colorBackground: Color = MaterialTheme.colors.primary
) {

    Surface(
        color = colorBackground,
        contentColor = Color.White
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
