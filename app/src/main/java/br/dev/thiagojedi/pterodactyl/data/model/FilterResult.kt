package br.dev.thiagojedi.pterodactyl.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Filter(
    val id: String,
    val title: String,
    val context: List<Context>,
    val expires_at: Date,
    @SerializedName("filter_action")
    val filterAction: FilterAction = FilterAction.WARN,
) {
    enum class FilterAction {
        @SerializedName("warn")
        WARN,

        @SerializedName("hide")
        HIDE
    }

    enum class Context {
        @SerializedName("home")
        HOME,

        @SerializedName("notifications")
        NOTIFICATIONS,

        @SerializedName("public")
        PUBLIC,

        @SerializedName("thread")
        THREAD,

        @SerializedName("account")
        ACCOUNT,
    }
}

data class FilterResult(
    val filter: Filter,
    val keyword_matches: List<String>,
    val status_matches: List<String>
)
