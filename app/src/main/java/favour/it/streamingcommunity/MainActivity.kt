package favour.it.streamingcommunity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import favour.it.streamingcommunity.api.StreamingViewModel
import favour.it.streamingcommunity.ui.theme.StreamingcommunityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[StreamingViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            StreamingcommunityTheme {
                Surface (
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePage(viewModel)
                }
            }
        }
    }
}

