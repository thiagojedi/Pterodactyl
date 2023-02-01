package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.data.model.Status

@Composable
fun TimeLineList(
    data: List<Status>,
    onNavigateToUser: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(data, key = { it.id }) {
            StatusItem(status = it, onUserClick = onNavigateToUser)
        }
    }
}

