package com.example.ccompmovietvshowlist.ui.data

data class MovieItem(
    val id: String,
    val title: String,
    val description: String,
    val link: String,
    val genre: MovieGenre,
    val watched: Boolean
)

enum class MovieGenre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    COMEDY,
}
