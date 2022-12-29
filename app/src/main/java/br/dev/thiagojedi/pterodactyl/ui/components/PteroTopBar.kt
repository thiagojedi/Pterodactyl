package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroTopBar() {
    CenterAlignedTopAppBar(
            title = { Text(text = "Pterodactyl") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
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