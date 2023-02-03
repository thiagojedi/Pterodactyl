package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Application
import br.dev.thiagojedi.pterodactyl.data.services.AccountService
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AppViewModel(context: Context) : ViewModel() {
    lateinit var clientId: String
    lateinit var clientSecret: String
    private val store: AppStore = AppStore(context)

    fun setClientInfo(application: Application) {
        clientId = application.client_id!!
        clientSecret = application.client_secret!!
    }

    private lateinit var _baseUrl: String
    suspend fun setBaseUrl(url: String) {
        _baseUrl = url
    }

    fun setUserToken(token: String) {
        viewModelScope.launch {
            store.saveBaseUrl(_baseUrl)
            store.saveUserToken(token)
        }
    }

    suspend fun validateUser() {
        val baseUrl = store.getBaseUrl.first()
        val userToken = store.getUserToken.first()

        if (baseUrl == null || userToken == null) {
            return
        }
        val api =
            RetrofitHelper.getInstance(baseUrl, userToken)
                .create(AccountService::class.java)
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

    val baseUrl = store.getBaseUrl
    val currentUserId = store.getCurrentAccountId
}