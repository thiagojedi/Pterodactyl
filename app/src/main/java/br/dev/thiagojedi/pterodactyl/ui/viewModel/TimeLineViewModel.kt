package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.data.services.TimeLineService
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TimeLineViewModel(context: Context) : ViewModel() {
    private val store: AppStore = AppStore(context)
    val timeline = mutableStateListOf<Status>()

    suspend fun getTimeline() {
        viewModelScope.launch {
            try {
                val baseUrl = store.getBaseUrl.first()!!
                val userToken = store.getUserToken.first()!!
                val api =
                    RetrofitHelper.getInstance(baseUrl, userToken).create(TimeLineService::class.java)
                val result = api.getHomeTimeline()

                result.body()?.let {
                    timeline.addAll(it)
                }
            } catch (ex: Exception) {
                Log.e(Companion.TAG, "getTimeline: ${ex.message}", )
            }
        }
    }

    companion object {
        private const val TAG = "TimeLineViewModel"
    }
}