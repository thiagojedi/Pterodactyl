package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import br.dev.thiagojedi.pterodactyl.ui.components.PteroNavBar
import br.dev.thiagojedi.pterodactyl.ui.components.Screen
import br.dev.thiagojedi.pterodactyl.ui.viewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroApp() {
    val appViewModel = AppViewModel(LocalContext.current)
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        appViewModel.validateUser()
    }

    NavHost(navController, "home_tabs") {
        navigation(Screen.Home.route, "home_tabs") {
            composable(Screen.Home.route) {
                Scaffold(bottomBar = { PteroNavBar(navController = navController) }) {
                    Surface(Modifier.padding(it)) {
                        HomeTimeLineView(
                            onNavigateToUser = { id ->
                                navController.navigate("profile/$id") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
            composable(Screen.Profile.route) {
                Scaffold(bottomBar = { PteroNavBar(navController = navController) }) {
                    Surface(Modifier.padding(it)) {
                        val userId = appViewModel.currentUserId.collectAsState(initial = null).value
                        if (userId != null) {
                            ProfileView(userId)
                        }
                    }
                }
            }
        }
        composable(
            "profile/{userId}"
        ) { entry ->
            ProfileView(
                entry.arguments?.getString("userId")!!,
                canGoBack = navController.previousBackStackEntry !== null,
                onGoBack = { navController.popBackStack() }
            )
        }
    }
}


