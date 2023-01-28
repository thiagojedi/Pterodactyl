package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import br.dev.thiagojedi.pterodactyl.ui.components.PteroNavBar
import br.dev.thiagojedi.pterodactyl.ui.components.PteroTopBar
import br.dev.thiagojedi.pterodactyl.ui.components.TootFab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroApp() {
    Scaffold(topBar = { PteroTopBar() },
        floatingActionButton = { TootFab() },
        bottomBar = { PteroNavBar() }) { padding ->
        Surface(Modifier.padding(padding)) {
            HomeTimeLineView()
        }
    }
}

