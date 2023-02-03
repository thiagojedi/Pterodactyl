package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(onLoginButtonClick: (String) -> Unit) {
    val (url, setUrl) = remember { mutableStateOf("") }

    PterodactylTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(value = url, onValueChange = setUrl, Modifier.fillMaxWidth())
            Button(
                onClick = { onLoginButtonClick(url) },
                modifier = Modifier.fillMaxWidth(),
                enabled = url.contains(Regex(".+\\..+"))
            ) {
                Text("Continue")
            }
        }
    }
}
