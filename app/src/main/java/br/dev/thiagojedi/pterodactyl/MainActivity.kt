package br.dev.thiagojedi.pterodactyl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.dev.thiagojedi.pterodactyl.ui.components.PteroNavBar
import br.dev.thiagojedi.pterodactyl.ui.components.PteroTopBar
import br.dev.thiagojedi.pterodactyl.ui.components.TootFab
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PterodactylTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                            topBar = { PteroTopBar() },
                            floatingActionButton = { TootFab() },
                            bottomBar = { PteroNavBar() }
                    ) {
                        Column(Modifier.padding(it)) {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Greeting("Android")
                            }

                            Card(modifier = Modifier.fillMaxWidth()) {
                                Greeting("Pterodactyl")
                            }

                            Card(modifier = Modifier.fillMaxWidth()) {
                                Greeting("Mastodon")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PterodactylTheme {
        Greeting("Android")
    }
}