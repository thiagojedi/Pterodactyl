package br.dev.thiagojedi.pterodactyl.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.data.services.StatusService
import br.dev.thiagojedi.pterodactyl.ui.components.PteroNavBar
import br.dev.thiagojedi.pterodactyl.ui.components.PteroTopBar
import br.dev.thiagojedi.pterodactyl.ui.components.TimeLineList
import br.dev.thiagojedi.pterodactyl.ui.components.TootFab
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroApp() {
    val viewModel = TimeLineViewModel()

    LaunchedEffect(Unit) {
        viewModel.getTimeline()
    }

    Scaffold(topBar = { PteroTopBar() },
        floatingActionButton = { TootFab() },
        bottomBar = { PteroNavBar() }) { padding ->
        Surface(Modifier.padding(padding)) {
            TimeLineList(data = viewModel.timeline)
        }
    }
}

class TimeLineViewModel : ViewModel() {
    val timeline = mutableStateListOf<Status>()

    suspend fun getTimeline() {
        viewModelScope.launch {
            val api = RetrofitHelper.getInstance().create(StatusService::class.java)
            val result = api.getPublicTimeline()

            result.body()?.let { timeline.addAll(it) }
        }
    }
}