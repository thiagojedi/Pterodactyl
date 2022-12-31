package br.dev.thiagojedi.pterodactyl.data.model

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class Status(
    val id: String,
    val created_at: Instant,
    @SerializedName("in_reply_to_id") val inReplyToId: String,
    @SerializedName("in_reply_to_account_id") val inReplyToAccountId: String,
    val sensitive: Boolean,
    @SerializedName("spoiler_text") val spoilerText: String,
    val visibility: String,
    val language: String,
    val uri: String,
    val url: String,
    @SerializedName("replies_count") val repliesCount: Int,
    @SerializedName("reblogs_count") val reblogsCount: Int,
    @SerializedName("favourites_count") val favouritesCount: Int,
    val favourited: Boolean,
    val reblogged: Boolean,
    val muted: Boolean,
    val bookmarked: Boolean,
    val content: String,
    val account: Account,
    val emojis: List<CustomEmoji>
)

