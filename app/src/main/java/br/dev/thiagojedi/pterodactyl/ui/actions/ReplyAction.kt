package br.dev.thiagojedi.pterodactyl.ui.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun ReplyAction(onClick: () -> Unit, enabled: Boolean = true) {
    IconButton(onClick, enabled = enabled) {
        Icon(
            Icons.Filled.Reply,
            contentDescription = "Reply",
        )
    }
}