package br.dev.thiagojedi.pterodactyl.data.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getInstance(url: String): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(url: String, token: String): Retrofit {
        return Retrofit.Builder().baseUrl(url)
            .client(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(newRequest)
                }
                .build())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}