package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Application
import retrofit2.Response
import retrofit2.http.*

interface AppService {
    @POST("/api/v1/apps")
    @FormUrlEncoded
    suspend fun createAppToken(
        @Field("client_name") clientName: String,
        @Field("redirect_uris") redirectUris: String,
        @Field("scopes") scopes: String,
        @Field("website") website: String,
    ): Response<Application>

    @GET("/api/v1/apps/verify_credentials")
    suspend fun validateCredentials(@Header("Authentication") authToken: String): Response<Application>
}