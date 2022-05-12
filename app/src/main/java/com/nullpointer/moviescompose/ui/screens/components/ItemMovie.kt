package com.nullpointer.moviescompose.ui.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R


@Composable
fun ItemMovie() {
    val painter = rememberImagePainter("https://picsum.photos/200/300") {
        placeholder(R.drawable.ic_movies)
        error(R.drawable.ic_broken_image)
        crossfade(true)
        crossfade(3_000)
    }
    Card(modifier = Modifier
        .height(200.dp)
        .width(150.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(5.dp)) {
        Box(modifier = Modifier.padding(10.dp)) {
            Image(painter, contentDescription = stringResource(R.string.description_img_movie))
            Text(text = "Este es el nombre de una pelicula muy largo",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
                    .align(Alignment.BottomCenter))
        }
    }
}