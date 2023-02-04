package br.dev.thiagojedi.pterodactyl.ui.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
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
fun BookmarkAction(status: Status, viewModel: StatusViewModel = viewModel()) {
    val tint = MaterialTheme.colorScheme.onSurface
    val selectedTint = MaterialTheme.colorScheme.primary

    val (bookmarked, setBookmarked) = remember {
        mutableStateOf(status.favourited)
    }
    val scope = rememberCoroutineScope()
    val onCheckedChange: (Boolean) -> Unit = { checked ->
        scope.launch {
            if (checked) {
                viewModel.bookmarkStatus(status.id)
            } else {
                viewModel.unbookmarkStatus(status.id)
            }
            setBookmarked(checked)
        }
    }

    IconToggleButton(checked = bookmarked, onCheckedChange = onCheckedChange) {
        Icon(
            if (bookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
            contentDescription = "Boost",
            tint = if (bookmarked) selectedTint else tint
        )
    }
}