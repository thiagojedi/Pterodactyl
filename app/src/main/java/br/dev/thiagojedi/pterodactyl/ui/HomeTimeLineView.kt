package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.ui.components.PteroTopBar
import br.dev.thiagojedi.pterodactyl.ui.components.TimeLineList
import br.dev.thiagojedi.pterodactyl.ui.viewModel.TimeLineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTimeLineView(
    timeLineViewModel: TimeLineViewModel = viewModel(),
    onNavigateToUser: (id: String) -> Unit = {},
    onReply: (id: String, mentions: List<String>) -> Unit = { id, mentions -> }
) {
    LaunchedEffect(Unit) {
        timeLineViewModel.getHomeTimeline()
    }
    Scaffold(
        topBar = { PteroTopBar() }
    ) {
        Surface(Modifier.padding(it)) {
            TimeLineList(data = timeLineViewModel.homeTimeLine, onNavigateToUser, onReply)
        }
    }
}