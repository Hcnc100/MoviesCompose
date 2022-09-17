package com.nullpointer.moviescompose.ui.screens.details.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.models.MovieDB
import com.nullpointer.moviescompose.ui.share.AsyncImageFade

@Composable
fun HeaderMovie(
    movie: MovieDB,
    paddingHeader: Float,
    alphaBackground: Float,
    modifier: Modifier = Modifier,
    colorBackground: Color = MaterialTheme.colors.primary
) {
    Box(
        modifier = modifier
            .background(colorBackground)
            .fillMaxWidth()
            .height(310.dp)
    ) {
        AsyncImageFade(
            data = movie.imgCover,
            contentDescription = R.string.description_background_img,
            resourceLoading = R.drawable.ic_movies,
            resourceFailed = R.drawable.ic_broken_image,
            modifier = Modifier
                .alpha(alphaBackground)
                .fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = .2f))
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            AsyncImageFade(
                data = movie.imgMovie,
                contentDescription = R.string.description_img_movie,
                resourceLoading = R.drawable.ic_movies,
                resourceFailed = R.drawable.ic_broken_image,
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
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

