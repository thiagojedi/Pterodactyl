package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.ui.components.ProfileDetails
import br.dev.thiagojedi.pterodactyl.ui.viewModel.ProfileViewModel

@Composable
fun ProfileView(
    userId: String,
    canGoBack: Boolean = false,
    onGoBack: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel(),
    onNavigateToUser: (String) -> Unit = {},
    currentUser: Boolean = false
) {
    LaunchedEffect(userId) {
        viewModel.getAccountDetails(userId)
    }
    val account = viewModel.accountDetails.value
    if (account != null) {
        ProfileDetails(
            account = account,
            canGoBack = canGoBack,
            onGoBack = onGoBack,
            onNavigateToUser,
            currentAccount = currentUser
        )
    }
}