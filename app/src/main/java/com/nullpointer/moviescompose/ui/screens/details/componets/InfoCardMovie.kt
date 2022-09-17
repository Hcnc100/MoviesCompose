package com.nullpointer.moviescompose.ui.screens.details.componets

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.moviescompose.R
import com.nullpointer.moviescompose.core.utils.convertTime
import com.nullpointer.moviescompose.models.MovieDB

@Composable
fun InfoCardMovie(
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
        OriginalTitleMovie(originalTitle = movieDB.title)
        Spacer(modifier = Modifier.height(10.dp))
        InfoMovie(
            info = stringResource(id = R.string.sub_title_value, voteAverage),
            iconInfo = R.drawable.ic_start
        )
        Spacer(modifier = Modifier.height(10.dp))
        InfoMovie(info = dateMovie, iconInfo = R.drawable.ic_time)
    }
}

@Composable
private fun InfoMovie(
    info: String,
    iconInfo: Int,
    modifier: Modifier = Modifier,
    contentDescriptionIcon: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconInfo),
            contentDescription = contentDescriptionIcon,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = info,
            color = Color.White,
            style = MaterialTheme.typography.caption
        )
    }
}


@Composable
private fun OriginalTitleMovie(
    originalTitle: String
) {
    Text(
        text = originalTitle,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3,
        style = MaterialTheme.typography.body1.copy(
            fontWeight = FontWeight.W500,
            color = Color.White,
            fontSize = 14.sp
        ),
    )
}