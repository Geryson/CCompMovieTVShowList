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
                title = "asdfmovie",
                description = "TomSka",
                link = "https://www.youtube.com/watch?v=tCnj-uiRCn8",
                genre = MovieGenre.COMEDY,
                watched = true
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