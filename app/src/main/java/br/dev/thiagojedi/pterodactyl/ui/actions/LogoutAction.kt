package br.dev.thiagojedi.pterodactyl.ui.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.ui.viewModel.AppViewModel
import kotlinx.coroutines.launch

@Composable
fun LogoutAction(
    viewModel: AppViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    IconButton(onClick = { scope.launch { viewModel.logoutUser() } }) {
        Icon(Icons.Filled.Logout, contentDescription = "settings")
    }
}