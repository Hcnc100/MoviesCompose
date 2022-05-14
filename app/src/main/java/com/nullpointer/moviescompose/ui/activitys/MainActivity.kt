package com.nullpointer.moviescompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.nullpointer.moviescompose.presentation.CastViewModel
import com.nullpointer.moviescompose.presentation.MoviesViewModel
import com.nullpointer.moviescompose.presentation.SearchViewModel
import com.nullpointer.moviescompose.ui.screens.NavGraphs
import com.nullpointer.moviescompose.ui.screens.movies.MoviesScreens
import com.nullpointer.moviescompose.ui.theme.MoviesComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val moviesViewModel:MoviesViewModel by viewModels()
    private val castViewModel:CastViewModel by viewModels()
    private val searchViewModel:SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesComposeTheme {
                DestinationsNavHost(
                    navController = rememberNavController(),
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(moviesViewModel)
                        dependency(castViewModel)
                        dependency(searchViewModel)
                    }
                )
            }
        }
    }
}

