package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TootFab() {
    FloatingActionButton(
            onClick = { /*TODO*/ },
    ) {
        Icon(Icons.Filled.Create, contentDescription = "Toot")
    }
}

@Preview
@Composable
fun TootFabPreview() {
    TootFab()
}