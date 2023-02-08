package br.dev.thiagojedi.pterodactyl

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import br.dev.thiagojedi.pterodactyl.data.store.AppStore
import br.dev.thiagojedi.pterodactyl.ui.PteroApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = AppStore(applicationContext)


        setContent {
            val savedUrl = dataStore.getBaseUrl.collectAsState(initial = "")
            if (savedUrl.value == null) {
                val intent = Intent(this, OnboardActivity::class.java)
                startActivity(intent)
                finish()
            }

            PteroApp()
        }
    }
}