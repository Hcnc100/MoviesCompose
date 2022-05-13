package com.nullpointer.moviescompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.nullpointer.moviescompose.ui.screens.NavGraphs
import com.nullpointer.moviescompose.ui.screens.movies.MoviesScreens
import com.nullpointer.moviescompose.ui.theme.MoviesComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesComposeTheme {
                DestinationsNavHost(
                    navController = rememberNavController(),
                    navGraph = NavGraphs.root,
                )
            }
        }
    }
}

