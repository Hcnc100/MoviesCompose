package com.nullpointer.moviescompose.ui.share

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R

@Composable
fun ImageMovieRequest(
    urlImg: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {

    val painter = rememberImagePainter(urlImg) {
        placeholder(R.drawable.ic_movies)
        error(R.drawable.ic_broken_image)
        crossfade(true)
    }
    Image(painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )

}