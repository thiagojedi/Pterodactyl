package br.dev.thiagojedi.pterodactyl.data.model

data class Application(
    val id: String,
    val name: String,
    val website: String?,
    val redirect_uri: String,
    val vapid_key: String,
    val client_id: String?,
    val client_secret: String?
)
