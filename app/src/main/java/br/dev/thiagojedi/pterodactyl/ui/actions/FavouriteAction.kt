package br.dev.thiagojedi.pterodactyl.ui.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.ui.viewModel.StatusViewModel
import kotlinx.coroutines.launch

@Composable
fun FavouriteAction(status: Status, viewModel: StatusViewModel = viewModel()) {
    val tint = MaterialTheme.colorScheme.onSurface
    val selectedTint = MaterialTheme.colorScheme.primary

    val (favorited, setFavorited) = remember {
        mutableStateOf(status.favourited)
    }
    val scope = rememberCoroutineScope()
    val onCheckedChange: (Boolean) -> Unit = { checked ->
        scope.launch {
            val response = if (checked) {
                viewModel.favouriteStatus(status.id)
            } else {
                viewModel.unfavouriteStatus(status.id)
            }
            if (response.isSuccessful) {
                setFavorited(checked)
            }
        }
    }

    IconToggleButton(checked = favorited, onCheckedChange = onCheckedChange) {
        Icon(
            if (favorited) Icons.Rounded.Star else Icons.Rounded.StarBorder,
            contentDescription = "Favorite",
            tint = if (favorited) selectedTint else tint
        )
    }
}