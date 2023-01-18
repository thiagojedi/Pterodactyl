package br.dev.thiagojedi.pterodactyl.data.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    // TODO: This should be dynamic, according to login
    val baseUrl = "https://cuscuz.in"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}