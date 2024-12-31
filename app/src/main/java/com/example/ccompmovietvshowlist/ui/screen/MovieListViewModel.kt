package com.example.ccompmovietvshowlist.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.ccompmovietvshowlist.ui.data.MovieGenre
import com.example.ccompmovietvshowlist.ui.data.MovieItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor() : ViewModel() {
    private val _movieList = mutableStateListOf<MovieItem>()

    init {
        addMovie(
            MovieItem(
                id = "1",
                isTVShow = false,
                title = "National Treasure",
                description = "A historian races to find the legendary Templar Treasure before a team of mercenaries.",
                link = "https://www.imdb.com/title/tt0368891/",
                genre = MovieGenre.ADVENTURE,
                watched = true,
                watchingTVShow = false
            )
        )
        addMovie(
            MovieItem(
                id = "2",
                isTVShow = true,
                title = "House M.D.",
                description = "Using a crack team of doctors and his wits, an antisocial maverick doctor specializing in diagnostic medicine does whatever it takes to solve puzzling cases that come his way.",
                link = "https://www.imdb.com/title/tt0412142/",
                genre = MovieGenre.DRAMA,
                watched = false,
                watchingTVShow = true
            )
        )
    }

    fun getAllMovies(): List<MovieItem> {
        return _movieList
    }
    fun addMovie(movie: MovieItem) {
        _movieList.add(movie)
    }
    fun removeMovie(movie: MovieItem) {
        _movieList.remove(movie)
    }
    fun changeWatchedStatus(movie: MovieItem, watched: Boolean) {
        val index = _movieList.indexOf(movie)

        val newMovie = movie.copy(
            title = movie.title,
            description = movie.description,
            genre = movie.genre,
            watched = watched
        )

        _movieList[index] = newMovie
    }

    fun clearAllMovies() {
        _movieList.clear()
    }

    fun editMovie(movieToEdit: MovieItem, movieEdited: MovieItem) {
        val index = _movieList.indexOf(movieToEdit)
        _movieList[index] = movieEdited
    }

    fun getMoviesInAscendingOrder() {
        _movieList.sortBy { it.title }
    }

    fun getMoviesInDescendingOrder() {
        _movieList.sortByDescending { it.title }
    }
}