package com.nullpointer.moviescompose.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager

class SearchScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val focusRequester: FocusRequester,
    private val focusManager: FocusManager
) : SimpleScreenState(context, scaffoldState) {

    fun requestFocus() {
        focusRequester.requestFocus()
    }

    fun hiddenKeyboard(){
        focusManager.clearFocus()
    }
}

@Composable
fun rememberSearchScreenState(
    context: Context = LocalContext.current,
    focusRequester: FocusRequester= FocusRequester(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    focusManager: FocusManager = LocalFocusManager.current
) = remember(scaffoldState) {
    SearchScreenState(context, scaffoldState, focusRequester, focusManager)
}