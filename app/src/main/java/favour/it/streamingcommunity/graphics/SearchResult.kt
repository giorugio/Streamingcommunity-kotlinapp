package favour.it.streamingcommunity.graphics

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import favour.it.streamingcommunity.api.SearchResult

@Composable
fun SearchResultsList(searchResults: List<SearchResult>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(searchResults) { result ->
            SearchResultItem(result = result)
        }
    }
}

@Composable
fun SearchResultItem(result: SearchResult) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Load the image
            val painter = rememberImagePainter(
                data = result.images.firstOrNull()?.filename,
                builder = {
                    crossfade(true)
                }
            )
            Image(
                painter = painter,
                contentDescription = result.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(
                    text = result.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Type: ${result.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Score: ${result.score}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
