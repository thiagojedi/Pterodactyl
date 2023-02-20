package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.dev.thiagojedi.pterodactyl.navigation.*
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.ui.viewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroApp(appViewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    val currentUserId = appViewModel.currentUserId.collectAsState(initial = null).value

    LaunchedEffect(Unit) {
        appViewModel.validateUser()
    }

    PterodactylTheme(dynamicColor = false) {
        NavHost(navController, "home_tabs") {
            homeRoutes("home_tabs", navController, currentUserId)

            profileRoutes(navController, currentUserId)

            composeRoutes(navController)
        }
    }
}


