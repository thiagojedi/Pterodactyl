package br.dev.thiagojedi.pterodactyl.data.services

import br.dev.thiagojedi.pterodactyl.data.model.Status
import retrofit2.Response
import retrofit2.http.GET

interface StatusService {
    @GET("/api/v1/timelines/public")
    suspend fun getPublicTimeline(): Response<List<Status>>
}