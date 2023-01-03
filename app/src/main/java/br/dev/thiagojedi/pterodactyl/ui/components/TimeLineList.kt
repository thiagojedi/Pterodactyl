package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier,
    ) {
        items(data) { item ->
            Column {
                StatusItem(item)
                Divider(modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

