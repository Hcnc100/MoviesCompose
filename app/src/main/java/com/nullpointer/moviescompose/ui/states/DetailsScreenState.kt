package com.nullpointer.moviescompose.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

class DetailsScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val toolbarState: CollapsingToolbarScaffoldState,
    val paddingIconNav: Float = 48f,
    val innerPadding: Float = 16f,
    val heightMinToolbar: Float = 48f,
    private val sizeMinTitle: Float = 18f,
    private val sizeMaxTitle: Float = 24f,
    private val topPaddingTitleExpanded: Float = 20f
) : SimpleScreenState(context, scaffoldState) {


    val progressCollapsing get() = toolbarState.toolbarState.progress
    val textSizeTitle get() = (sizeMinTitle + (sizeMaxTitle - sizeMinTitle) * progressCollapsing)
    val paddingTopTitle get() = ((paddingIconNav + topPaddingTitleExpanded) * progressCollapsing)
    val paddingStartTitle get() = (innerPadding + ((paddingIconNav - innerPadding) * (1 - progressCollapsing)))
}


@Composable
fun rememberDetailsScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    toolbarState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
) = remember(scaffoldState, toolbarState) {
    DetailsScreenState(context, scaffoldState, toolbarState)
}