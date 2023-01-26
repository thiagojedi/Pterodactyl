package br.dev.thiagojedi.pterodactyl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import br.dev.thiagojedi.pterodactyl.ui.PteroApp
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = AppStore(applicationContext)

        setContent {
            val savedUrl = dataStore.getBaseUrl.collectAsState(initial = "")
            if (savedUrl.value == null) {
                val intent = Intent(this, OnboardActivity::class.java)
                startActivity(intent)
            }

            PterodactylTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PteroApp()
                }
            }
        }
    }
}