package favour.it.streamingcommunity.graphics

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(val label: String, val icon: ImageVector)

object BottomNavItems {
    val items = listOf(
        BottomNavItem("Home", Icons.Filled.Home),
        BottomNavItem("New", Icons.Filled.OndemandVideo),
        BottomNavItem("Search", Icons.Filled.Search),
        BottomNavItem("Account", Icons.Filled.AccountCircle),
    )

}