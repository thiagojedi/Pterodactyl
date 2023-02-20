package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.app.Application

class StatusViewModel(context: Application) : PteroViewModel(context) {
    suspend fun reblogStatus(id: String) = api.reblogStatus(id)

    suspend fun unreblogStatus(id: String) = api.unreblogStatus(id)

    suspend fun favouriteStatus(id: String) = api.favouriteStatus(id)

    suspend fun unfavouriteStatus(id: String) = api.unfavouriteStatus(id)

    suspend fun bookmarkStatus(id: String) = api.bookmarkStatus(id)

    suspend fun unbookmarkStatus(id: String) = api.unbookmarkStatus(id)

    suspend fun createStatus(status: String, contentWarning: String?, inReplyTo: String?) =
        api.postNewStatus(
            status,
            spoilerText = contentWarning,
            inReplyTo = inReplyTo
        )
}