package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.ui.components.ProfileDetails
import br.dev.thiagojedi.pterodactyl.ui.viewModel.AppViewModel
import br.dev.thiagojedi.pterodactyl.ui.viewModel.ProfileViewModel

@Composable
fun ProfileView(
    userId: String? = null,
    canGoBack: Boolean = false,
    onGoBack: () -> Unit = {},
    appViewModel: AppViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel(),
    onNavigateToUser: (String) -> Unit = {},
    currentUser: Boolean = false
) {
    val currentUserId = appViewModel.currentUserId.collectAsState(initial = null).value
    LaunchedEffect(userId, currentUserId) {
        if (!userId.isNullOrBlank()) profileViewModel.getAccountDetails(userId)
        else if (!currentUserId.isNullOrBlank()) {
            profileViewModel.getAccountDetails(currentUserId)
        }
    }
    val account = profileViewModel.accountDetails.value
    if (account != null) {
        ProfileDetails(
            account = account,
            canGoBack = canGoBack,
            onGoBack = onGoBack,
            onNavigateToUser,
            currentAccount = currentUser || currentUserId == userId
        )
    }
}