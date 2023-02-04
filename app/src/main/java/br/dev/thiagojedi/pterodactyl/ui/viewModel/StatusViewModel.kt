package br.dev.thiagojedi.pterodactyl.ui.viewModel

import android.app.Application
import android.util.Log

private val TAG = StatusViewModel::class.simpleName

class StatusViewModel(context: Application) : PteroViewModel(context) {
    suspend fun reblogStatus(id: String) {
        try {
            api.reblogStatus(id)
        } catch (ex: Exception) {
            Log.e(TAG, "reblogStatus: $ex")
        }
    }

    suspend fun unreblogStatus(id: String) {
        try {
            api.unreblogStatus(id)
        } catch (ex: Exception) {
            Log.e(TAG, "unreblogStatus: $ex")
        }
    }

    suspend fun favouriteStatus(id: String) {
        try {
            api.favouriteStatus(id)
        } catch (ex: Exception) {
            Log.e(TAG, "favouriteStatus: $ex")
        }
    }

    suspend fun unfavouriteStatus(id: String) {
        try {
            api.unfavouriteStatus(id)
        } catch (ex: Exception) {
            Log.e(TAG, "unfavouriteStatus: $ex")
        }
    }

    suspend fun bookmarkStatus(id: String) {
        try {
            api.bookmarkStatus(id)
        } catch (ex: Exception) {
            Log.e(TAG, "bookmarkStatus: $ex")
        }
    }

    suspend fun unbookmarkStatus(id: String) {
        try {
            api.unbookmarkStatus(id)
        } catch (ex: Exception) {
            Log.e(TAG, "unbookmarkStatus: $ex")
        }
    }
}