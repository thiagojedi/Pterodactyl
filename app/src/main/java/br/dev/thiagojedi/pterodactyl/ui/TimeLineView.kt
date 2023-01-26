package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import br.dev.thiagojedi.pterodactyl.ui.components.TimeLineList
import br.dev.thiagojedi.pterodactyl.ui.viewModel.TimeLineViewModel

@Composable
fun TimeLineView() {
    val timeLineViewModel = TimeLineViewModel(LocalContext.current)

    LaunchedEffect(Unit) {
        timeLineViewModel.getTimeline()
    }

    TimeLineList(data = timeLineViewModel.timeline)
}