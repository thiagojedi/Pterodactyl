package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.ui.components.PteroTopBar
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(onLoginButtonClick: (String) -> Unit) {
    val (url, setUrl) = remember { mutableStateOf("") }

    PterodactylTheme {
        Scaffold(topBar = { PteroTopBar() }) { padding ->
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = url,
                    onValueChange = setUrl,
                    Modifier
                        .padding(bottom = 8.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .fillMaxWidth(),
                    label = { Text(text = "Your Instance") },
                    placeholder = {
                        Text(text = "Example: mastodon.social")
                    },
                    singleLine = true
                )
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewLoginView() {
    PterodactylTheme {
        LoginView(onLoginButtonClick = {})
    }
}
