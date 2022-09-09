package com.nullpointer.moviescompose.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

class MoviesScreenState(
    context: Context,
    scaffoldState: ScaffoldState
):SimpleScreenState(context, scaffoldState)

@Composable
fun rememberMoviesScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) = remember(scaffoldState) {
    MoviesScreenState(context, scaffoldState)
}