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
    scaffoldState: ScaffoldState,
    context: Context,
    focusManager: FocusManager
):SimpleScreenState(
    scaffoldState, context, focusManager
)

@Composable
fun rememberMoviesScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current
) = remember(scaffoldState) {
    MoviesScreenState(scaffoldState, context, focusManager)
}