package com.nullpointer.moviescompose.core.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
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