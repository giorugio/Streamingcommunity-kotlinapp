package favour.it.streamingcommunity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import favour.it.streamingcommunity.api.SearchResult
import favour.it.streamingcommunity.api.StreamingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModels: StreamingViewModel) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var selectedItemIndex by remember { mutableStateOf(0) }
//    val focusRequester = remember { FocusRequester() } //questo richiede il focus
//    val focusManager = LocalFocusManager.current //questo controlla il focus

    //viewmodel
    val searchResponse by viewModels.searchResponse.observeAsState()
    val isLoading by viewModels.isLoading.observeAsState(false)
    val error by viewModels.error.observeAsState()

//    LaunchedEffect(isSearchExpanded) {
//        if (isSearchExpanded) {
//            focusRequester.requestFocus()
//        } else {
//            focusManager.clearFocus()
//        }
//    }

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
                            .fillMaxWidth()
                            .padding(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (!isSearchExpanded) {
                            Text(
                                text = "StreamingCommunity",
                                fontSize = 21.sp,
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
                                .weight(1f),
//                                .focusRequester(focusRequester),
                            singleLine = true,
//                            keyboardOptions = KeyboardOptions.Default.copy(
//                                imeAction = ImeAction.Search
//                            ),
//                            keyboardActions = KeyboardActions(
//                                onSearch = {
//                                    viewModels.search(searchText)
//                                    cleanSearch()
//                                }
//                            )
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
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = selectedItemIndex == 0,
                        onClick = { selectedItemIndex = 0 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Movie, contentDescription = "Film") },
                        label = { Text("Film") },
                        selected = selectedItemIndex == 1,
                        onClick = { selectedItemIndex = 1 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = selectedItemIndex == 2,
                        onClick = { selectedItemIndex = 2 }
                    )
                }
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
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
                            text = "Errore: $error",
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp)
                        )
                    }
                    searchResponse != null -> {
                        //SearchResultsList(searchResults = searchResponse!!.results.values.toList())
                        searchResponse?.let { SearchResultsList(searchResults = it)  }
                    }
                }
            }
        }
    )

}

