package br.dev.thiagojedi.pterodactyl.ui.viewModel

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
                api.verifyCredentials().body()?.let {
                    store.saveAccountId(it.id)
                }
            } catch (ex: Exception) {
                store.clearBaseUrl()
            }
        }
    }

    val currentUserId = store.getCurrentAccountId
}