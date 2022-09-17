package com.nullpointer.moviescompose.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.nullpointer.moviescompose.ui.states.SimpleScreenState
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

class DetailsScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val toolbarState: CollapsingToolbarScaffoldState
) : SimpleScreenState(context, scaffoldState) {
    val progressCollapsing get() = toolbarState.toolbarState.progress
}


@Composable
fun rememberDetailsScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    toolbarState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
) = remember(scaffoldState, toolbarState) {
    DetailsScreenState(context, scaffoldState, toolbarState)
}