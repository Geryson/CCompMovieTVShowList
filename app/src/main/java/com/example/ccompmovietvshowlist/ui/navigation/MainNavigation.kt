package com.example.ccompmovietvshowlist.ui.navigation

sealed class MainNavigation(val route: String) {
    object MainMovieScreen : MainNavigation("mainmoviescreen")

}