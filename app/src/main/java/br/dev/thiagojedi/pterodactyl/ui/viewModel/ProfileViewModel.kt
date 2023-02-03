package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.services.AccountService
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(context: Application) : AndroidViewModel(context) {
    private val store: AppStore = AppStore(context)
    val accountDetails = mutableStateOf<Account?>(null)

    suspend fun getAccountDetails(id: String) {
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
                val response = api.getAccount(id)

                response.body()?.let {
                    accountDetails.value = it
                }
            } catch (ex: Exception) {
                Log.e(TAG, "getAccountDetails: ${ex.message}")
            }
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}