package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.Application
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.model.StatusContext
import retrofit2.Response
import retrofit2.http.*

interface MastodonApiService {
    //#region Status
    @GET("/api/v1/statuses/{id}")
    suspend fun getStatus(@Path("id") id: String): Response<Status>

    @DELETE("/api/v1/statuses/{id}")
    suspend fun deleteStatus(@Path("id") id: String): Response<Status>

    @GET("/api/v1/statuses/{id}/context")
    suspend fun getStatusContext(@Path("id") id: String): Response<StatusContext>

    @POST("/api/v1/statuses/{id}/favourite")
    suspend fun favouriteStatus(@Path("id") id: String): Response<Status>

    @POST("/api/v1/statuses/{id}/unfavourite")
    suspend fun unfavouriteStatus(@Path("id") id: String): Response<Status>

    @POST("/api/v1/statuses/{id}/reblog")
    suspend fun reblogStatus(@Path("id") id: String): Response<Status>

    @POST("/api/v1/statuses/{id}/unreblog")
    suspend fun unreblogStatus(@Path("id") id: String): Response<Status>

    @POST("/api/v1/statuses/{id}/pin")
    suspend fun pinStatus(@Path("id") id: String): Response<Status>

    @POST("/api/v1/statuses/{id}/unpin")
    suspend fun unpinStatus(@Path("id") id: String): Response<Status>

    @POST("api/v1/statuses/{id}/bookmark")
    suspend fun bookmarkStatus(@Path("id") statusId: String): Response<Status>

    @POST("api/v1/statuses/{id}/unbookmark")
    suspend fun unbookmarkStatus(@Path("id") statusId: String): Response<Status>

    @FormUrlEncoded
    @POST("/api/v1/statuses")
    suspend fun postNewStatus(
        @Field("status") status: String,
        @Field("media_ids") mediaIds: List<String> = emptyList(),
        @Field("in_reply_to_id") inReplyTo: String? = null,
        @Field("sensitive") sensitive: Boolean = false,
        @Field("spoiler_text") spoilerText: String? = null,
        @Field("visibility") visibility: Status.Visibility = Status.Visibility.PRIVATE,
    ): Response<Status>

    //#endregion
    //#region Timeline
    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(@Query("local") local: Boolean = false): Response<List<Status>>

    @GET("/api/v1/timelines/tag/{hashtag}")
    suspend fun getHashtagTimeline(@Path("hashtag") hashtag: String): Response<List<Status>>

    @GET("/api/v1/timelines/list/{list_id}")
    suspend fun getListTimeline(@Path("list_id") listId: String): Response<List<Status>>

    @GET("/api/v1/timelines/home")
    suspend fun getHomeTimeline(): Response<List<Status>>

    //#endregion
    //#region Application
    @FormUrlEncoded
    @POST("/api/v1/apps")
    suspend fun createAppToken(
        @Field("client_name") clientName: String,
        @Field("redirect_uris") redirectUris: String,
        @Field("scopes") scopes: String,
        @Field("website") website: String,
    ): Response<Application>

    @GET("/api/v1/apps/verify_credentials")
    suspend fun validateCredentials(@Header("Authentication") authToken: String): Response<Application>

    //#endregion
    //#region Account
    @GET("/api/v1/accounts/{id}")
    suspend fun getAccount(@Path("id") id: String): Response<Account>

    @GET("/api/v1/accounts/verify_credentials")
    suspend fun verifyCredentials(): Response<Account>
    //#endregion
    //#region OAuth
    @FormUrlEncoded
    @POST("/oauth/revoke")
    suspend fun revokeToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("token") token: String
    ): Response<Unit>
    //#endregion
}