package com.nullpointer.moviescompose.core.utils

import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.*
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

fun Long.convertTime(): String? {
    val date = Date(this)
    val format: Format = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
    return format.format(date)
}


@Composable
inline fun <reified VM : ViewModel> shareViewModel():VM {
    val activity= LocalContext.current as ComponentActivity
    return hiltViewModel(activity)
}

fun ViewModel.launchSafeIO(
    blockBefore: suspend CoroutineScope.() -> Unit = {},
    blockAfter: suspend CoroutineScope.(Boolean) -> Unit = {},
    blockException: suspend CoroutineScope.(Exception) -> Unit = {},
    blockIO: suspend CoroutineScope.() -> Unit,
): Job {
    var isForCancelled = false
    return viewModelScope.launch {
        try {
            blockBefore()
            withContext(Dispatchers.IO) { blockIO() }
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> {
                    isForCancelled = true
                    throw e
                }
                else -> blockException(e)
            }
        } finally {
            blockAfter(isForCancelled)
        }
    }
}

fun Modifier.myShimmer(
    shimmer: Shimmer,
): Modifier = composed {
    val color by animateColorAsState(if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray)
    shimmer(shimmer).background(color)
}
