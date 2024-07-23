package favour.it.streamingcommunity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import favour.it.streamingcommunity.api.StreamingViewModel
import favour.it.streamingcommunity.graphics.BottomNavItems
import favour.it.streamingcommunity.graphics.ChipsResults
import favour.it.streamingcommunity.graphics.SearchResultsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModels: StreamingViewModel) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val items = BottomNavItems.items
    var selectedItemIndex by remember { mutableStateOf(0) }
    var selectedTag by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() } //questo richiede il focus
    val focusManager = LocalFocusManager.current //questo controlla il focus

    //viewmodel
    val searchResponse by viewModels.searchResponse.observeAsState()
    val genreResponse by viewModels.genreResponse.observeAsState()
    val isLoading by viewModels.isLoading.observeAsState(false)
    val error by viewModels.error.observeAsState()

    //"top10 trending", "latest", "upcoming",

    val tags = listOf(
        "Korean drama", "Romance", "Avventura", "Soap",
        "Western", "Commedia", "War & Politics", "Thriller", "televisione film", "Guerra", "Reality",
        "Storia", "Fantasy", "Mistero", "Animazione", "Musica", "Fantascenza", "Horror", "Crime",
        "Documentario", "Sci-Fi & Fantasy", "Azione", "Action & Adventure", "Dramma", "Famiglia", "Kids"
    )

    LaunchedEffect(isSearchExpanded) {
        if (isSearchExpanded) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
        }
    }

    fun cleanSearch() {
        searchText = ""
        isSearchExpanded = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.streamingcommunitylogo),
                            contentDescription = "StreamingCommunity",
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        if (!isSearchExpanded) {
                            Text(
                                text = "Streaming Community",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                actions = {
                    if (isSearchExpanded) {
                        IconButton(onClick = { cleanSearch() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            label = { Text("Cerca film e serie tv") },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    viewModels.search(searchText)
                                    cleanSearch()
                                }
                            )
                        )
                        IconButton(onClick = {
                            viewModels.search(searchText)
                            cleanSearch()
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    } else {
                        IconButton(onClick = { isSearchExpanded = true }) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBar (containerColor = Color.Transparent){
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = { selectedItemIndex = index },
                            icon = { Icon(item.icon, contentDescription = item.label, modifier = Modifier.size(25.dp)) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column {
                    LazyRow(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth(),
                    ) {
                        items(tags) { tag ->
                            val selected = selectedTag == tag
                            FilterChip(
                                onClick = {
                                    selectedTag = if (selectedTag == tag) null else tag
                                    viewModels.getTitlesByGenre(tag)
                                },
                                label = { Text(tag) },
                                selected = selected,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    when {
                        error != null -> {
                            Text(
                                text = "Error: $error",
                                color = Color.Red,
                                modifier = Modifier
                            )
                        }
                        genreResponse != null -> {
                            genreResponse?.let { ChipsResults(titles = it) }
                        }
                    }
                }


                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Center)
                        )
                    }
                    error != null -> {
                        Text(
                            text = "Error: $error",
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        )
                    }
                    searchResponse != null -> {
                        searchResponse?.let { SearchResultsList(searchResults = it)  }
                    }
                }
            }
        }
    )

}

