package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.dev.thiagojedi.pterodactyl.data.services.MastodonApiService
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

abstract class PteroViewModel(context: Application) : AndroidViewModel(context) {
    protected lateinit var api: MastodonApiService
    private val store: AppStore = AppStore(context)

    init {
        runBlocking {
            val baseUrl = store.getBaseUrl.first()
            val userToken = store.getUserToken.first()

            if (baseUrl != null && userToken != null) {
                api =
                    RetrofitHelper.getInstance(baseUrl, userToken)
                        .create(MastodonApiService::class.java)
            }
        }
    }
}