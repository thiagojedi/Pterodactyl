package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PteroNavBar() {
    NavigationBar {
        NavigationBarItem(selected = true,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                label = { Text("Home") })
        NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Search, contentDescription = "Explore") },
                label = { Text("Explore") })
        NavigationBarItem(selected = false,
                onClick = { /*TODO*/ },
                icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notifications") },
                label = { Text("Notifications") })
    }
}