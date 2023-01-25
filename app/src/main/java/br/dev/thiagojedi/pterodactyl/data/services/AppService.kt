package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Application
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AppService {
    @POST("/api/v1/apps")
    suspend fun createAppToken(
        @Field("client_name") clientName: String,
        @Field("redirect_uris") redirectUris: String,
        @Field("scopes") scopes: String,
        @Field("website") website: String,
    ): Application

    @GET("/api/v1/apps/verify_credentials")
    suspend fun validateCredentials(@Header("Authentication") authToken: String): Application
}