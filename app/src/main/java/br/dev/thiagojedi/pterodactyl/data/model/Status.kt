package br.dev.thiagojedi.pterodactyl.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Status(
    val id: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("in_reply_to_id") val inReplyToId: String?,
    @SerializedName("in_reply_to_account_id") val inReplyToAccountId: String?,
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
    val emojis: List<CustomEmoji>,
    @SerializedName("media_attachments") val mediaAttachments: List<MediaAttachment>
) {
    enum class MediaAttachmentType {
        @SerializedName("unknown")
        UNKNOWN,

        @SerializedName("image")
        IMAGE,

        @SerializedName("gifv")
        GIF,

        @SerializedName("video")
        VIDEO,

        @SerializedName("audio")
        AUDIO
    }

    data class MediaAttachment(
        val id: String,
        val type: MediaAttachmentType,
        val url: String,
        @SerializedName("preview_url") val previewUrl: String,
        @SerializedName("remote_url") val remoteUrl: String?,
        val description: String?,
        @SerializedName("blurhash") val blurHash: String,
        val meta: ImageMeta,
    ) {
        data class MetaSize(val width: Int, val height: Int)
        data class ImageMeta(val original: MetaSize?, val small: MetaSize?)
    }
}
