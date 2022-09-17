package com.nullpointer.moviescompose.ui.screens.details.componets

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.models.Cast
import com.nullpointer.moviescompose.ui.share.AsyncImageFade

@Composable
fun ItemCast(
    cast: Cast,
) {
    Card(modifier = Modifier.padding(4.dp), shape = RoundedCornerShape(10.dp)) {
        Box(
            Modifier
                .height(200.dp)
                .width(150.dp)
        ) {
            AsyncImageFade(
                data = cast.urlImg,
                modifier = Modifier.fillMaxSize(),
                contentDescription = R.string.description_img_actor,
                resourceLoading = R.drawable.ic_person,
                resourceFailed = R.drawable.ic_error_person,
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