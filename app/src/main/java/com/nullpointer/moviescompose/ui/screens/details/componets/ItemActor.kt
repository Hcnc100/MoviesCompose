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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.getCorrectGrayColor
import com.nullpointer.moviescompose.models.Cast

@Composable
fun ItemCast(
    cast: Cast,
) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(cast.urlImg)
            .build(),
        placeholder = painterResource(id = R.drawable.ic_person),
        error = painterResource(id =R.drawable.ic_error_person),
    )
    Card(modifier = Modifier.padding(4.dp), shape = RoundedCornerShape(10.dp)) {
        Box(
            Modifier
                .height(200.dp)
                .width(150.dp)
        ) {
            Image(
                painter = painter,
                modifier = Modifier.fillMaxSize(),
                contentDescription = stringResource(R.string.description_img_actor),
                colorFilter = if (painter.state is AsyncImagePainter.State.Success) null else ColorFilter.tint(
                    getCorrectGrayColor().value
                ),
                contentScale = if (painter.state is AsyncImagePainter.State.Success) ContentScale.Crop else ContentScale.Fit,
            )
            Box(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = .2f))
                    .fillMaxSize()
            )
            Text(
                text = cast.name,
                style = MaterialTheme.typography.caption,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}