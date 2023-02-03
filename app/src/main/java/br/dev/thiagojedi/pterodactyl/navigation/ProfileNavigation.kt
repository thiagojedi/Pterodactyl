package br.dev.thiagojedi.pterodactyl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.dev.thiagojedi.pterodactyl.ui.ProfileView

fun NavController.navigateToProfile(id: String) {
    this.navigate("profile/$id")
}

fun NavGraphBuilder.profileRoutes(navController: NavController) {
    composable("profile/{userId}") { entry ->
        ProfileView(
            entry.arguments?.getString("userId")!!,
            canGoBack = navController.previousBackStackEntry !== null,
            onGoBack = { navController.popBackStack() }
        )
    }
}