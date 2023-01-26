package br.dev.thiagojedi.pterodactyl.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val BASE_URL_KEY = stringPreferencesKey("base_url")
val USER_TOKEN = stringPreferencesKey("user_token")

class AppStore(private val context: Context) {
    suspend fun saveBaseUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[BASE_URL_KEY] = url
        }
    }

    suspend fun saveUserToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    suspend fun clearBaseUrl() {
        context.dataStore.edit { preferences ->
            preferences.remove(BASE_URL_KEY)
        }
    }

    val getBaseUrl = context.dataStore.data.map { prefs -> prefs[BASE_URL_KEY] }
    val getUserToken = context.dataStore.data.map { prefs -> prefs[USER_TOKEN] }
}