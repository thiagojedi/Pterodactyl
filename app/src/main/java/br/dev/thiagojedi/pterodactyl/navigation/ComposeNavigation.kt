package br.dev.thiagojedi.pterodactyl.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.dev.thiagojedi.pterodactyl.ui.ComposeView

fun NavController.navigateToCompose() {
    this.navigate("compose")
}

fun NavController.navigateToReply(replyTo: String, mentions: List<String> = emptyList()) {
    this.navigate("compose?replyId=$replyTo&mentions=${mentions.joinToString()}")
}

fun NavGraphBuilder.composeRoutes(navController: NavController) {
    composable(
        "compose?replyId={replyId}&mentions={mentions}",
        listOf(
            navArgument("replyId") {
                nullable = true
            },
            navArgument("mentions") {
                nullable = true
            }
        )
    ) { entry ->
        val replyId = entry.arguments?.getString("replyId")
        val mentions = entry.arguments?.getString("mentions")?.split(", ")

        ComposeView(
            replyId,
            mentions = mentions,
            onPostSuccess = { navController.popBackStack() },
            onClose = { navController.popBackStack() })
    }
}