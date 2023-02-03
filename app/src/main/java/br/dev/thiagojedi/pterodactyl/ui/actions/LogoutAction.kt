package br.dev.thiagojedi.pterodactyl.ui.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.launch

@Composable
fun LogoutAction() {
    val scope = rememberCoroutineScope()
    val dataStore = AppStore(context = LocalContext.current)
    IconButton(onClick = { scope.launch { dataStore.clearBaseUrl() } }) {
        Icon(Icons.Filled.Logout, contentDescription = "settings")
    }
}