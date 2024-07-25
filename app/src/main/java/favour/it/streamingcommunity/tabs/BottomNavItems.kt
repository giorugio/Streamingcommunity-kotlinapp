package favour.it.streamingcommunity.tabs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector
)

object BottomNavItems {
    val items = listOf(
        BottomNavItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
        BottomNavItem("Esplora", Icons.Outlined.Search, Icons.Filled.Search),
        BottomNavItem("Upcoming", Icons.Outlined.OndemandVideo, Icons.Filled.OndemandVideo),
        BottomNavItem("Libreria", Icons.Rounded.Bookmark, Icons.Filled.Bookmark),
    )
}