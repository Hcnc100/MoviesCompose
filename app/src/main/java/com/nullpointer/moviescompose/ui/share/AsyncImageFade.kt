package com.nullpointer.moviescompose.ui.share

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.nullpointer.moviescompose.core.utils.getGrayColor
import com.nullpointer.moviescompose.core.utils.isSuccess

@Composable
fun AsyncImageFade(
    data: Any?,
    @DrawableRes
    resourceLoading: Int,
    @DrawableRes
    resourceFailed: Int,
    modifier: Modifier = Modifier,
    @StringRes
    contentDescription: Int
) {

    val painterImg = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .crossfade(true)
            .data(data)
            .build(),
        placeholder = painterResource(id = resourceLoading),
        error = painterResource(id = resourceFailed)
    )
    Image(
        painter = painterImg,
        contentDescription = stringResource(id = contentDescription),
        colorFilter = if (painterImg.isSuccess) null else ColorFilter.tint(getGrayColor()),
        contentScale = if (painterImg.isSuccess) ContentScale.Crop else ContentScale.Fit,
        modifier = modifier
    )
}