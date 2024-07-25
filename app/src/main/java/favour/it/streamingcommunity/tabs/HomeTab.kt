package favour.it.streamingcommunity.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import favour.it.streamingcommunity.api.StreamingViewModel
import favour.it.streamingcommunity.api.Title

@Composable
fun HomeTab(
    titles: List<Title>,
    tags: List<String>,
    viewModels: StreamingViewModel,
    innerPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            MainContentBig(titles.firstOrNull(), innerPadding)
        }

        items(titles.drop(1)) { title ->
            MainContentSmall(title)
        }

    }
}

@Composable
fun MainContentSmall(title: Title) {
    val backgroundImage = title.images.find { it.type == "cover_mobile" }?.filename
    val imageUrl = "https://cdn.streamingcommunity.photos/images/${backgroundImage}"



}

@Composable
fun MainContentBig(title: Title?, innerPadding: PaddingValues) {
    title?.let {
        val backgroundImage = title.images.find { it.type == "background" }?.filename
        val logoImage = title.images.find { it.type == "logo" }?.filename
        val imageUrl = "https://cdn.streamingcommunity.photos/images/${backgroundImage}"
        val logoUrl = "https://cdn.streamingcommunity.photos/images/${logoImage}"

        Box (
            modifier = Modifier,
        ) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = title.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(16f / 16f)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = size.height - 500f,
                                endY = size.height
                            )
                        )
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 1000f
                        )
                    )
                    .align(Alignment.BottomCenter)
            )

            Column (
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(logoUrl),
                    contentDescription = title.name,
                    modifier = Modifier
                        .height(80.dp)
                        .size(400.dp)
                )
//                Text(
//                    text = title.name,
//                    style = MaterialTheme.typography.titleMedium.copy(
//                        color = Color.White, fontSize = 24.sp
//                    )
//                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Button(
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors( Color.White ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.Black,
                        )
                        Text("Riproduci", color = Color.Black, modifier = Modifier.padding(end = 8.dp))
                    }

                    Button(
                        onClick = {  },
                        colors = ButtonDefaults.buttonColors( Color.Gray ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Altre info", color = Color.White, modifier = Modifier.padding(end = 8.dp)
                        )

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShowChips (
    tags: List<String>,
    viewModels: StreamingViewModel,
    innerPadding: PaddingValues
) {
    var selectedTag by remember { mutableStateOf<String?>(null) }
    val genreResponse by viewModels.genreResponse.observeAsState()

    Column (
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
    ) {
        LazyRow (
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            contentPadding = PaddingValues(4.dp)
        ) {
            items(tags.chunked(tags.size / 2)) { row ->
                FlowRow (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { tag ->
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
            }
        }
        genreResponse?.let { ChipsFetch(titles = it) }
    }
}

@Composable
fun ChipsFetch(titles: List<Title>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(titles) { title ->
            ChipsFetchRepresentation(title)
        }
    }
}

@Composable
fun ChipsFetchRepresentation(title: Title) {
    val poster = title.images.find { it.type == "poster" }

    poster?.let { image ->
        val imageUrl = "https://cdn.streamingcommunity.photos/images/${image.filename}"

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(12.dp))
                .shadow(8.dp, RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
                .clickable { /* Handle click */ }
        ) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = title.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .padding(4.dp)
            ) {
                Text(
                    text = title.name,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
        }
    }
}