package br.dev.thiagojedi.pterodactyl.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import br.dev.thiagojedi.pterodactyl.ui.HomeTimeLineView
import br.dev.thiagojedi.pterodactyl.ui.ProfileView
import br.dev.thiagojedi.pterodactyl.ui.components.PteroNavBar
import br.dev.thiagojedi.pterodactyl.ui.components.Screen
import br.dev.thiagojedi.pterodactyl.ui.components.TootFab

@ExperimentalMaterial3Api
fun NavGraphBuilder.homeRoutes(route: String, navController: NavController) {
    navigation(Screen.Home.route, route) {
        composable(Screen.Home.route) {
            Scaffold(bottomBar = { PteroNavBar(navController = navController) },
                floatingActionButton = { TootFab(onClick = { navController.navigateToCompose() }) }) {
                Surface(Modifier.padding(it)) {
                    HomeTimeLineView(onNavigateToUser = { id -> navController.navigateToProfile(id) },
                        onReply = { id, mentions -> navController.navigateToReply(id, mentions) })
                }
            }
        }
        composable(Screen.Profile.route) {
            Scaffold(bottomBar = { PteroNavBar(navController = navController) }) {
                Surface(Modifier.padding(it)) {
                    ProfileView(
                        currentUser = true,
                        onNavigateToUser = { id -> navController.navigateToProfile(id) })
                }
            }
        }
    }
}