package br.dev.thiagojedi.pterodactyl.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Account(
    val id: String,
    val username: String,
    val acct: String,
    val display_name: String,
    val locked: Boolean,
    val bot: Boolean,
    @SerializedName("create_at") val createdAt: Date,
    val note: String,
    val url: String,
    val avatar: String,
    @SerializedName("avatar_static") val avatarStatic: String,
    val header: String,
    @SerializedName("header_static") val headerStatic: String,
    @SerializedName("followers_count") val followersCount: Int,
    @SerializedName("following_count") val followingCount: Int,
    @SerializedName("statuses_count") val statusesCount: Int,
    @SerializedName("last_status_at") val lastStatusAt: Date,
    val emojis: List<CustomEmoji>,
    val fields: List<Field>,
) {
    data class Field(
        val name: String,
        val value: String,
        @SerializedName("verified_at") val verifiedAt: Date,
    )
}
