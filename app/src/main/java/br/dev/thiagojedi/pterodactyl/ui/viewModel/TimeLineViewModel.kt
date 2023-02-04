package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Filter
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.launch

class TimeLineViewModel(context: Application) : PteroViewModel(context) {
    private val store: AppStore = AppStore(context)
    val homeTimeLine = mutableStateListOf<Status>()
    val localTimeLine = mutableStateListOf<Status>()
    val federatedTimeLine = mutableStateListOf<Status>()

    suspend fun getHomeTimeline() = getTimeline("home", homeTimeLine)
    suspend fun getFederatedTimeline() = getTimeline("federated", federatedTimeLine)
    suspend fun getLocalTimeline() = getTimeline("local", localTimeLine)

    private suspend fun getTimeline(type: String, list: MutableList<Status>) {
        viewModelScope.launch {
            try {
                val result = when (type) {
                    "home" -> api.getHomeTimeline()
                    "local" -> api.getPublicTimeline(local = true)
                    else -> api.getPublicTimeline()
                }

                result.body()?.let { newItems ->
                    val filteredStatus =
                        newItems.filterNot {
                            it.filtered != null && it.filtered.any { filterResult -> filterResult.filter.filterAction == Filter.FilterAction.HIDE }
                        }

                    if (filteredStatus.size < newItems.size) {
                        Log.d(TAG, "getTimeline: statuses were filtered")
                    }

                    list.addAll(elements = filteredStatus, index = 0)
                }
            } catch (ex: Exception) {
                Log.e(TAG, "getTimeline: $type - ${ex.message}")
            }
        }
    }

    companion object {
        private const val TAG = "TimeLineViewModel"
    }
}