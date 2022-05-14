package com.nullpointer.moviescompose.ui.screens.details.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.models.Cast

@Composable
fun ItemCast(
    cast: Cast,
) {
    val painter = rememberImagePainter(data = cast.urlImg) {
        placeholder(R.drawable.ic_person)
        error(R.drawable.ic_error_person)
        crossfade(true)
    }
    Card(modifier = Modifier.padding(4.dp), shape = RoundedCornerShape(10.dp)) {
        Box(Modifier
            .height(200.dp)
            .width(150.dp)) {
            Image(painter = painter,
                contentDescription = "Imagen del actor",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.background(Color.Black.copy(alpha = .3f)))
            Text(text = cast.name,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .align(Alignment.BottomCenter),
                style = MaterialTheme.typography.caption,
                fontSize = 18.sp
            )
        }
    }
}