package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.data.store.AppStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView() {
    val dataStore = AppStore(LocalContext.current)
    val scope = rememberCoroutineScope()

    val (url, setUrl) = remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        TextField(value = url, onValueChange = setUrl, Modifier.fillMaxWidth())
        Button(
            onClick = {
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = url.contains(Regex("https://.+\\..+"))
        ) {
            Text("Continue")
        }
        Spacer(modifier = Modifier.height(32.dp))
        val savedUrl = dataStore.getBaseUrl.collectAsState(initial = "")

        Text(text = savedUrl.value.orEmpty())
    }
}
