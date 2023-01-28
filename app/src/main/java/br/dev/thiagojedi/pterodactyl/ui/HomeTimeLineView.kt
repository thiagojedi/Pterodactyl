package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.ui.components.TimeLineList
import br.dev.thiagojedi.pterodactyl.ui.viewModel.TimeLineViewModel

@Composable
fun HomeTimeLineView(timeLineViewModel: TimeLineViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        timeLineViewModel.getHomeTimeline()
    }

    TimeLineList(data = timeLineViewModel.homeTimeLine)
}