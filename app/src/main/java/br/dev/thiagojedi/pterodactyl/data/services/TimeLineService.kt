package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Status
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TimeLineService {
    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(@Query("local") local: Boolean = false): Response<List<Status>>

    @GET("/api/v1/timelines/tag/{hashtag}")
    suspend fun getHashtagTimeline(@Path("hashtag") hashtag: String): Response<List<Status>>

    @GET("/api/v1/timelines/list/{list_id}")
    suspend fun getListTimeline(@Path("list_id") listId: String): Response<List<Status>>

    @GET("/api/v1/timelines/home")
    suspend fun getHomeTimeline(): Response<List<Status>>
}