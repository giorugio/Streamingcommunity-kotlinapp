package favour.it.streamingcommunity

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import favour.it.streamingcommunity.api.StreamingViewModel
import favour.it.streamingcommunity.tabs.BottomNavItems
import favour.it.streamingcommunity.graphics.SearchResultsList
import favour.it.streamingcommunity.tabs.HomeTab

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomePage(viewModels: StreamingViewModel) {
    val items = BottomNavItems.items
    var selectedItemIndex by remember { mutableStateOf(0) }
    var isProfileDialogVisible by remember { mutableStateOf(false) }

    //viewmodel
    val homeData by viewModels.homeData.observeAsState(emptyList())
    val searchResponse by viewModels.searchResponse.observeAsState()
    val isLoading by viewModels.isLoading.observeAsState(false)
    val error by viewModels.error.observeAsState()

    //"top10 trending", "latest", "upcoming",

    val tags = listOf(
        "Korean drama", "Romance", "Avventura", "Soap",
        "Western", "Commedia", "War & Politics", "Thriller",
        "televisione film", "Guerra", "Reality", "Storia", "Fantasy",
        "Mistero", "Animazione", "Musica", "Fantascenza", "Horror", "Crime",
        "Documentario", "Sci-Fi & Fantasy", "Azione", "Action & Adventure",
        "Dramma", "Famiglia", "Kids"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.streamingcommunitylogo),
                            contentDescription = "StreamingCommunity",
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "StreamingCommunity",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { isProfileDialogVisible= true }) {
                            Icon(Icons.Filled.AccountCircle, contentDescription = "Account")
                        }
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        bottomBar = {
            BottomAppBar (containerColor = Color.Black.copy(alpha = 0.5f)) {
                NavigationBar (containerColor = Color.Transparent){
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = { selectedItemIndex = index },
                            icon = {
                                AnimatedContent(
                                    targetState = selectedItemIndex == index,
                                    transitionSpec = { fadeIn() with fadeOut() }
                                ) {
                                    isSelected ->
                                        Icon(
                                            imageVector = if(isSelected) {
                                                item.filledIcon
                                            } else {
                                                item.outlinedIcon
                                            },
                                            contentDescription = item.label,
                                            modifier = Modifier.size(25.dp)
                                        )
                                    }
                                },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when(selectedItemIndex) {
                    0 -> HomeTab(homeData, tags, viewModels, innerPadding)
                    1 -> Text(text = "")
                    2 -> Text(text = "")
                    3 -> Text(text = "")
                    4 -> Text(text = "")
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
    if (isProfileDialogVisible) {
        ProfileDialog(
            onDismissRequest = { isProfileDialogVisible = false },
            viewModels = viewModels
        )
    }
}

@Composable
fun ProfileDialog(onDismissRequest: () -> Unit, viewModels: StreamingViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.padding(1.dp),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.AccountCircle, contentDescription = "Account", modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("StreamingCommunity-User", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("email@email.com", fontSize = 14.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                ProfileItem("Watchlist", Icons.AutoMirrored.Filled.List,onClick = {

                })
                ProfileItem("Continua a guardare", Icons.Filled.PlayCircle ,onClick = {

                })
                ProfileItem("Impostazioni", Icons.Filled.Settings ,onClick = {

                })
                ProfileItem("Aiuto & Feedback", Icons.AutoMirrored.Filled.Help,onClick = {

                })
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                ProfileItem("Logout", Icons.AutoMirrored.Filled.Logout,onClick = {

                })
            }
        }
    }
}

@Composable
fun ProfileItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 16.sp)
    }
}

