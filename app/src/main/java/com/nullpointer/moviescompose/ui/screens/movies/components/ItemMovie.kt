package com.nullpointer.moviescompose.ui.screens.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.models.MovieDB


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemMovie(
    movie: MovieDB,
    actionClick: (MovieDB) -> Unit,
) {
    val painter = rememberImagePainter(movie.imgMovie) {
        placeholder(R.drawable.ic_movies)
        error(R.drawable.ic_broken_image)
        crossfade(true)
    }
    Card(modifier = Modifier
        .height(200.dp)
        .width(150.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { actionClick(movie) }) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.description_img_movie),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f)))
            Text(
                text = movie.title,
                textAlign = TextAlign.Center,
                maxLines = 2,
                style = MaterialTheme.typography.h6,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .align(Alignment.BottomCenter))
        }
    }
}