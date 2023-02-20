package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TootFab(text: String = "Toot", onClick: () -> Unit = {}) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Outlined.Create, "Localized description") },
        text = { Text(text) },
    )
}

@Preview
@Composable
private fun TootFabPreview() {
    val mock = "Postar"

    TootFab(mock)
}

@Preview
@Composable
private fun TootFabPreviewDois() {
    val mock = "Escrever"

    TootFab(mock)
}

