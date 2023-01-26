package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroTopBar() {
    val dataStore = AppStore(LocalContext.current)
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = { Text(text = "Pterodactyl") },
        actions = {
            IconButton(onClick = {
                scope.launch {
                    dataStore.clearBaseUrl()
                }
            }) {
                Icon(Icons.Filled.Settings, contentDescription = "settings")
            }
        }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    PteroTopBar()
}