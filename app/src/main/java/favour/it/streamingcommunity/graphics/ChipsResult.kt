package favour.it.streamingcommunity.graphics

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import favour.it.streamingcommunity.api.Title

@Composable
fun ChipsResults(titles: List<Title>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(titles) { title ->
            TitleCard(title)
        }
    }
}

@Composable
fun TitleCard(title: Title) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {

            },
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            if (title.images.isNotEmpty()) {
                val imageUrl = title.images.firstOrNull { it.type == "poster" }?.filename
                val painter = rememberImagePainter("https://example.com/path/to/images/$imageUrl") // Replace with actual URL base
                Image(
                    painter = painter,
                    contentDescription = title.name,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp)
                )
            }
            Column {
                Text(
                    text = title.name,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Score: ${title.score}",
                    fontSize = 14.sp
                )
                Text(
                    text = "Type: ${title.type}",
                    fontSize = 14.sp
                )
            }
        }
    }
}