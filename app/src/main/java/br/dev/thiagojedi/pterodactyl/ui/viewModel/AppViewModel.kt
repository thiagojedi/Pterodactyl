package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Application
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.launch

class AppViewModel(context: android.app.Application) : PteroViewModel(context) {
    lateinit var clientId: String
    lateinit var clientSecret: String
    private val store: AppStore = AppStore(context)

    fun setClientInfo(application: Application) {
        clientId = application.client_id!!
        clientSecret = application.client_secret!!
    }

    private lateinit var _baseUrl: String

    fun setBaseUrl(url: String) {
        _baseUrl = url
    }

    fun setUserToken(token: String) {
        viewModelScope.launch {
            store.saveBaseUrl(_baseUrl)
            store.saveUserToken(token)
        }
    }

    suspend fun validateUser() {
        viewModelScope.launch {
            try {
                val response = api.verifyCredentials()
                if (!response.isSuccessful && response.code() == 401) {
                    store.clearBaseUrl()
                }
            } catch (ex: Exception) {
                Log.e(AppViewModel::class.simpleName, "validateUser: $ex")
            }
        }
    }

    val currentUserId = store.getCurrentAccountId
}