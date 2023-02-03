package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Account
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AccountService {
    @GET("/api/v1/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): Response<Account>

    @GET("/api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(): Response<Account>
}