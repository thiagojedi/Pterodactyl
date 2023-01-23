package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.model.StatusContext
import retrofit2.http.*

interface StatusService {
    @GET("/api/v1/statuses/{id}")
    suspend fun getStatus(@Path("id") id: String): Status

    @DELETE("/api/v1/statuses/{id}")
    suspend fun deleteStatus(@Path("id") id: String): Status

    @GET("/api/v1/statuses/{id}/context")
    suspend fun getStatusContext(@Path("id") id: String): StatusContext

    @POST("/api/v1/statuses/{id}/favourite")
    suspend fun favouriteStatus(@Path("id") id: String): Status

    @POST("/api/v1/statuses/{id}/unfavourite")
    suspend fun unfavouriteStatus(@Path("id") id: String): Status

    @POST("/api/v1/statuses/{id}/reblog")
    suspend fun reblogStatus(@Path("id") id: String): Status

    @POST("/api/v1/statuses/{id}/unreblog")
    suspend fun unreblogStatus(@Path("id") id: String): Status

    @POST("/api/v1/statuses/{id}/pin")
    suspend fun pinStatus(@Path("id") id: String): Status

    @POST("/api/v1/statuses/{id}/unpin")
    suspend fun unpinStatus(@Path("id") id: String): Status

    @FormUrlEncoded
    @POST("/api/v1/statuses")
    suspend fun postNewStatus(
        @Field("status") status: String,
        @Field("media_ids") mediaIds: List<String> = emptyList(),
        @Field("in_reply_to_id") inReplyTo: String? = null,
        @Field("sensitive") sensitive: Boolean = false,
        @Field("spoiler_text") spoilerText: String? = null,
        @Field("visibility") visibility: Status.Visibility,
    ): Status
}

