package br.dev.thiagojedi.pterodactyl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.dev.thiagojedi.pterodactyl.ui.ProfileView

fun NavController.navigateToProfile(id: String) {
    this.navigate("profile/$id")
}

fun NavGraphBuilder.profileRoutes(navController: NavController, currentUserId: String?) {
    composable("profile/{userId}") { entry ->
        val userId = entry.arguments?.getString("userId")!!

        ProfileView(
            userId,
            canGoBack = navController.previousBackStackEntry !== null,
            onGoBack = { navController.popBackStack() },
            onNavigateToUser = { navController.navigateToProfile(it) },
            currentUser = userId == currentUserId
        )
    }
}