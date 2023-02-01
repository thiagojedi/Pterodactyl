package br.dev.thiagojedi.pterodactyl.ui

import ProfileView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.dev.thiagojedi.pterodactyl.data.model.mock.FakeAccount
import br.dev.thiagojedi.pterodactyl.ui.components.PteroNavBar
import br.dev.thiagojedi.pterodactyl.ui.components.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { PteroNavBar(navController) }
    ) { padding ->
        NavHost(navController, Screen.Home.route, Modifier.padding(padding)) {
            composable(Screen.Home.route) {
                HomeTimeLineView(
                    onNavigateToUser = { id ->
                        navController.navigate("profile/$id") {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Profile.route) { ProfileView(account = FakeAccount) }

            composable(
                "profile/{userId}"
            ) { entry ->
                ProfileView(
                    account = FakeAccount,
                    entry.arguments?.getString("userId"),
                    canGoBack = navController.previousBackStackEntry !== null,
                    onGoBack = { navController.popBackStack() }
                )
            }
        }
    }
}

