package br.dev.thiagojedi.pterodactyl.ui.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.data.services.TimeLineService
import kotlinx.coroutines.launch

class TimeLineViewModel : ViewModel() {
    val timeline = mutableStateListOf<Status>()

    suspend fun getTimeline() {
        viewModelScope.launch {
            val api = RetrofitHelper.getInstance().create(TimeLineService::class.java)
            val result = api.getPublicTimeline()

            result.body()?.let { timeline.addAll(it) }
        }
    }
}