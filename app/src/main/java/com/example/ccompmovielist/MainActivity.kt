package com.example.ccompmovietvshowlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ccompmovietvshowlist.ui.screen.MovieListScreen
import com.example.ccompmovietvshowlist.ui.navigation.MainNavigation
import com.example.ccompmovietvshowlist.ui.theme.CCompMovieTVShowListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CCompMovieTVShowListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieListAppNavHost()
                }
            }
        }
    }
}

@Composable
fun MovieListAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.MainMovieScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = MainNavigation.MainMovieScreen.route) {
            MovieListScreen(navController = navController)
        }
    }
}