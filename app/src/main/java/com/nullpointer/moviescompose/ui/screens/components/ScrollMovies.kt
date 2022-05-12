package com.nullpointer.moviescompose.ui.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ScrollMovies(
    titleMovies:String
) {
    Column {
        Text(titleMovies)
        Row(modifier = Modifier.fillMaxWidth()) {

        }
    }
}