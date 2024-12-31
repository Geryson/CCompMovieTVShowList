package com.example.ccompmovietvshowlist.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ccompmovietvshowlist.R
import com.example.ccompmovietvshowlist.ui.data.MovieGenre
import com.example.ccompmovietvshowlist.ui.data.MovieItem
import com.example.ccompmovietvshowlist.util.SearchTopAppBar
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    movieListViewModel: MovieListViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var showAddMovieDialog by rememberSaveable { mutableStateOf(false) }

    var movieToEdit: MovieItem? by rememberSaveable { mutableStateOf(null) }

    var searchText by rememberSaveable { mutableStateOf("") }
    var searchAppBarState by remember { mutableStateOf(false) }

    var selectedBottomTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            if (!searchAppBarState) {
                TopAppBar(
                    title = { Text(text = "Movie List") },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    actions = {
                        IconButton(onClick = {
                            movieListViewModel.clearAllMovies()
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                        IconButton(onClick = {
                            movieListViewModel.getMoviesInAscendingOrder()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.sort_alpha_asc),
                                contentDescription = null,
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                        IconButton(onClick = {
                            movieListViewModel.getMoviesInDescendingOrder()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.sort_alpha_desc),
                                contentDescription = null,
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                        IconButton(onClick = {
                            searchAppBarState = true
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    }
                )
            } else {
                SearchTopAppBar(
                    text = searchText,
                    onTextChange = {
                        searchText = it
                    },
                    onCloseClicked = {
                        searchAppBarState = false
                        searchText = ""
                    },
                    onSearchClicked = {
                        searchAppBarState = false
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddMovieDialog = true
                    movieToEdit = null
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,
        bottomBar = {
            BottomAppBar(content = {
                NavigationBar(
                    //containerColor = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    NavigationBarItem(selected = selectedBottomTab == 0,
                        onClick = { selectedBottomTab = 0 },
                        label = {
                            Text(
                                text = "Movies",
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.movie_recorder_svgrepo_com),
                                contentDescription = "Movies",
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        })
                    NavigationBarItem(selected = selectedBottomTab == 1,
                        onClick = { selectedBottomTab = 1 },
                        label = {
                            Text(
                                text = "TV Shows",
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.tv_svgrepo_com),
                                contentDescription = "TV Shows",
                                modifier = Modifier.size(AssistChipDefaults.IconSize)
                            )
                        })
                }
            })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showAddMovieDialog) {
                    MovieForm(
                        onDialogClose = { showAddMovieDialog = false },
                        movieListViewModel = movieListViewModel,
                        movieToEdit = movieToEdit
                    )
                }

                if (movieListViewModel.getAllMovies().isEmpty()) {
                    Text(text = "No movies to show")
                } else {
                    LazyColumn(modifier = Modifier.fillMaxHeight()) {
                        items(movieListViewModel.getAllMovies().filter {
                            it.isTVShow == (selectedBottomTab == 1)
                        }) {
                            if (searchText.isEmpty() || it.title.contains(
                                    searchText,
                                    ignoreCase = true
                                )
                            ) {
                                MovieCard(it,
                                    onRemoveMovie = {
                                        movieListViewModel.removeMovie(it)
                                    },
                                    onMovieCheckChange = { checked ->
                                        movieListViewModel.changeWatchedStatus(it, checked)
                                    },
                                    onEditMovie = {
                                        showAddMovieDialog = true
                                        movieToEdit = it
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieCard(
    movie: MovieItem,
    onMovieCheckChange: (Boolean) -> Unit = {},
    onRemoveMovie: () -> Unit = {},
    onEditMovie: (MovieItem) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(5.dp)
            .animateContentSize()
    ) {
        var expanded by remember { mutableStateOf(false) }

        ConstraintLayout(
            modifier = Modifier
                .defaultMinSize(100.dp)
                .fillMaxWidth()
        ) {
            val (title, checkbox, deleteButton, editButton, iconExpanded) = createRefs()

            Text(
                movie.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                    end.linkTo(checkbox.start, margin = 10.dp)
                    horizontalBias = 0f
                },
                textAlign = TextAlign.Start
            )
            Checkbox(
                checked = movie.watched,
                onCheckedChange = onMovieCheckChange,
                Modifier
                    .scale(1.5f)
                    .constrainAs(checkbox) {
                        top.linkTo(parent.top, margin = 10.dp)
                        end.linkTo(deleteButton.start, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    }
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .clickable { onRemoveMovie() }
                    .size(20.dp)
                    .constrainAs(deleteButton) {
                        end.linkTo(iconExpanded.start, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    },
                tint = Color.Red,

                )
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "Edit",
                modifier = Modifier
                    .clickable { onEditMovie(movie) }
                    .size(20.dp)
                    .constrainAs(editButton) {
                        top.linkTo(parent.top, margin = 10.dp)
                        end.linkTo(iconExpanded.start, margin = 10.dp)
                    },
                tint = Color.Blue
            )
            IconButton(
                onClick = {
                    expanded = !expanded
                },
                modifier = Modifier.constrainAs(iconExpanded) {
                    top.linkTo(parent.top, margin = 10.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Show less" else "Show more"
                )
            }
        }
        if (expanded) {
            Text(
                text = movie.description,
                modifier = Modifier.padding(10.dp)
            )
            if (movie.isTVShow) {
                Text(
                    text = if (movie.watchingTVShow) "Already watching" else "Not watched yet",
                    color = if (movie.watchingTVShow) Color(0xFF00A900) else Color.Black,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun MovieForm(
    movieListViewModel: MovieListViewModel = viewModel(),
    onDialogClose: () -> Unit = {},
    movieToEdit: MovieItem? = null
) {
    var isTVShow by remember { mutableStateOf(movieToEdit?.isTVShow ?: false) }
    var newMovieTitle by remember { mutableStateOf(movieToEdit?.title ?: "") }
    var newMovieDescription by remember { mutableStateOf(movieToEdit?.description ?: "") }
    var newMovieLink by remember { mutableStateOf(movieToEdit?.link ?: "") }
    var newMovieGenre by remember { mutableStateOf(movieToEdit?.genre ?: MovieGenre.ACTION) }
    var newMovieWatched by remember { mutableStateOf(movieToEdit?.watched ?: false) }
    var watchingNewTVShow by remember { mutableStateOf(movieToEdit?.watchingTVShow ?: false) }

    Dialog(
        onDismissRequest = onDialogClose
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            isTVShow = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isTVShow) Color.Blue else Color.LightGray, // Filled background for selected
                            contentColor = if (!isTVShow) Color.White else Color.Black // Text color contrast
                        )
                    ) {
                        Text(text = "Movie")
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.movie_recorder_svgrepo_com),
                            contentDescription = "Movies",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                    Button(
                        onClick = {
                            isTVShow = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isTVShow) Color.Blue else Color.LightGray, // Filled background for selected
                            contentColor = if (isTVShow) Color.White else Color.Black // Text color contrast
                        )
                    ) {
                        Text(text = "TV Show")
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.tv_svgrepo_com),
                            contentDescription = "TV Shows",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                }
                OutlinedTextField(
                    value = newMovieTitle,
                    label = { Text(text = "Title") },
                    onValueChange = { newMovieTitle = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.width(10.dp))
                OutlinedTextField(
                    value = newMovieDescription,
                    label = { Text(text = "Description") },
                    onValueChange = { newMovieDescription = it },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = newMovieLink,
                    label = { Text(text = "Link") },
                    onValueChange = { newMovieLink = it },
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(MovieGenre.entries.toTypedArray()) { genre ->
                        Button(
                            onClick = { newMovieGenre = genre },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (genre == newMovieGenre) Color.Blue else Color.LightGray, // Filled background for selected
                                contentColor = if (genre == newMovieGenre) Color.White else Color.Black // Text color contrast
                            )
                        ) {
                            Text(genre.name)
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = newMovieWatched,
                        onCheckedChange = { newMovieWatched = !newMovieWatched }
                    )
                    Text(text = "Important")
                }
                if (isTVShow) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = watchingNewTVShow,
                            onCheckedChange = { watchingNewTVShow = !watchingNewTVShow }
                        )
                        Text(text = "Already watching")
                    }
                }
                Row {
                    Button(onClick = {
                        if (movieToEdit == null) {
                            movieListViewModel.addMovie(
                                MovieItem(
                                    id = UUID.randomUUID().toString(),
                                    isTVShow = isTVShow,
                                    title = newMovieTitle,
                                    description = newMovieDescription,
                                    link = newMovieLink,
                                    genre = newMovieGenre,
                                    watched = newMovieWatched,
                                    watchingTVShow = watchingNewTVShow
                                )
                            )
                        } else {
                            val movieEdited = movieToEdit.copy(
                                isTVShow = isTVShow,
                                title = newMovieTitle,
                                description = newMovieDescription,
                                link = newMovieLink,
                                genre = newMovieGenre,
                                watched = newMovieWatched,
                                watchingTVShow = watchingNewTVShow
                            )

                            movieListViewModel.editMovie(movieToEdit, movieEdited)
                        }

                        onDialogClose()

                    }) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun GenreSelector(modifier: Modifier = Modifier) {
//    var selectedIndex by remember { mutableIntStateOf(0) }
//    val options = MovieGenre.entries.map { it.name }
//
//    Column {
//        options.forEachIndexed { index, label ->
//            Button(
//                shape = SegmentedButtonDefaults.itemShape(
//                    index = index,
//                    count = options.size
//                ),
//                onClick = { selectedIndex = index },
//                selected = index == selectedIndex,
//                label = { Text(text = label) },
//            )
//        }
//    }
//}

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(
        movie = MovieItem(
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
}

//@Preview
//@Composable
//fun MovieFormPreview() {
//    MovieForm()
//}

//@Preview
//@Composable
//fun MovieListScreenPreview() {
//    MovieListScreen(navController = NavHostController(LocalContext.current))
//}