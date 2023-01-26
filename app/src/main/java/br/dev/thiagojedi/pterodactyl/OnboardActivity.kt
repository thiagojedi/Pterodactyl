package br.dev.thiagojedi.pterodactyl

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.data.model.Application
import br.dev.thiagojedi.pterodactyl.data.services.AppService
import br.dev.thiagojedi.pterodactyl.data.services.RetrofitHelper
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.ui.viewModel.AppViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.openid.appauth.*

@OptIn(ExperimentalMaterial3Api::class)
class OnboardActivity : ComponentActivity() {
    private val TAG = "OnboardActivity"
    private lateinit var service: AuthorizationService
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AppViewModel(this)

        service = AuthorizationService(this)

        setContent {
            val scope = rememberCoroutineScope()

            PterodactylTheme {
                val (url, setUrl) = remember { mutableStateOf("") }

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    TextField(value = url, onValueChange = setUrl, Modifier.fillMaxWidth())
                    Button(
                        onClick = {
                            scope.launch {
                                registerApp(url)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = url.contains(Regex("https://.+\\..+"))
                    ) {
                        Text("Continue")
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    val savedUrl = viewModel.baseUrl.collectAsState(initial = "")

                    Text(text = savedUrl.value.orEmpty())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        service.dispose()
    }

    private val redirectUri = "pterodactyl://callback"
    private val scopes = "read write follow push"

    suspend fun registerApp(url: String) {
        coroutineScope {
            val api = RetrofitHelper.getInstance(url).create(AppService::class.java)
            val result = api.createAppToken(
                clientName = "Pterodactyl",
                redirectUris = redirectUri,
                scopes = scopes,
                website = "https://github.com/thiagojedi/Pterodactyl"
            )

            if (
                result.isSuccessful) {
                val app = result.body()!!

                viewModel.setBaseUrl(url)
                viewModel.setClientInfo(app)


                login(url, app)
            }
        }
    }

    private fun login(url: String, app: Application) {
        val redirectUri = Uri.parse(this.redirectUri)
        val authorizationUri = Uri.parse(url + "/oauth/authorize")
        val tokenUri = Uri.parse(url + "/oauth/token")
        val configuration =
            AuthorizationServiceConfiguration(authorizationUri, tokenUri)
        val request = AuthorizationRequest.Builder(
            configuration,
            app.client_id!!,
            ResponseTypeValues.CODE,
            redirectUri
        )
            .setScopes(scopes)
            .build()
        val intent = service.getAuthorizationRequestIntent(request)
        launcher.launch(intent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val ex = AuthorizationException.fromIntent(it.data)
                val result = AuthorizationResponse.fromIntent(it.data!!)
                if (ex != null) {
                    Log.e(TAG, "launcher: $ex")
                } else {
                    val secret = ClientSecretBasic(viewModel.clientSecret)
                    val tokenRequest = result?.createTokenExchangeRequest()

                    service.performTokenRequest(tokenRequest!!, secret) { res, exception ->
                        if (exception != null) {
                            Log.e(TAG, "launcher: ${exception.error}")
                        } else {
                            val token = res?.accessToken


                            viewModel.setUserToken(token!!)
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
}