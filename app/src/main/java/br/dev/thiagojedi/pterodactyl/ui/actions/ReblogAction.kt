package br.dev.thiagojedi.pterodactyl.ui.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.RepeatOn
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
fun ReblogAction(status: Status, viewModel: StatusViewModel = viewModel()) {
    val tint = MaterialTheme.colorScheme.onSurface
    val selectedTint = MaterialTheme.colorScheme.primary
    val enabled =
        status.visibility != Status.Visibility.UNKNOWN
                && status.visibility != Status.Visibility.DIRECT
                && status.visibility != Status.Visibility.PRIVATE

    val (boosted, setBoosted) = remember {
        mutableStateOf(status.reblogged)
    }
    val scope = rememberCoroutineScope()
    val onCheckedChange: (Boolean) -> Unit = { checked ->
        scope.launch {
            if (checked) {
                viewModel.reblogStatus(status.id)
            } else {
                viewModel.unreblogStatus(status.id)
            }
            setBoosted(checked)
        }
    }

    IconToggleButton(enabled = enabled, checked = boosted, onCheckedChange = onCheckedChange) {
        Icon(
            if (boosted) Icons.Rounded.RepeatOn else Icons.Rounded.Repeat,
            contentDescription = "Boost",
            tint = if (boosted) selectedTint else tint
        )
    }
}