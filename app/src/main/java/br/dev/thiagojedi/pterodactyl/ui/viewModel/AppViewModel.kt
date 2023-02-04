package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Application
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

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
        runBlocking {
            store.saveBaseUrl(_baseUrl)
            store.saveClientInfo(clientId, clientSecret)
            store.saveUserToken(token)
        }
    }

    fun logoutUser() {
        runBlocking {
            val clientId = store.getClientId.first()!!
            val clientSecret = store.getClientSecret.first()!!
            val token = store.getUserToken.first()!!
            val result = api.revokeToken(clientId, clientSecret, token)

            if (result.isSuccessful) {
                store.clearBaseUrl()
            }
        }
    }

    suspend fun validateUser() {
        viewModelScope.launch {
            try {
                val response = api.verifyCredentials()
                if (!response.isSuccessful && response.code() == 401) {
                    store.clearBaseUrl()
                } else {
                    response.body()?.let { store.saveAccountId(it.id) }
                }
            } catch (ex: Exception) {
                Log.e(AppViewModel::class.simpleName, "validateUser: $ex")
            }
        }
    }

    val currentUserId = store.getCurrentAccountId
}