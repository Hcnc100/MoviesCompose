package com.nullpointer.moviescompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.nullpointer.moviescompose.ui.screens.NavGraphs
import com.nullpointer.moviescompose.ui.theme.MoviesComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isSplash=true
        installSplashScreen().apply {
            setKeepOnScreenCondition{ isSplash }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) { delay(1500)}
            isSplash=false
        }
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

