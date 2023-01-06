package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.data.model.Status

@Composable
fun TimeLineList(data: List<Status>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(data) { item ->
            Column {
                StatusItem(item)
                Divider()
            }
        }
    }
}

