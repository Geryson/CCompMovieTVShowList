package com.example.ccompmovietvshowlist.ui.data

data class MovieItem(
    val id: String,
    val isTVShow: Boolean,
    val title: String,
    val description: String,
    val link: String,
    val genre: MovieGenre,
    val watched: Boolean,
    val watchingTVShow: Boolean
)

enum class MovieGenre {
    ACTION,
    ADVENTURE,
    ANIMATION,
    COMEDY,
    DRAMA,
}
