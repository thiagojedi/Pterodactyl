package br.dev.thiagojedi.pterodactyl.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Status(
    val id: String,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("in_reply_to_id") val inReplyToId: String?,
    @SerializedName("in_reply_to_account_id") val inReplyToAccountId: String?,
    val sensitive: Boolean,
    @SerializedName("spoiler_text") val spoilerText: String?,
    val visibility: Visibility,
    val language: String? = null,
    val uri: String,
    val url: String,
    @SerializedName("replies_count") val repliesCount: Int,
    @SerializedName("reblogs_count") val reblogsCount: Int,
    @SerializedName("favourites_count") val favouritesCount: Int,
    @SerializedName("edited_at") val editedAt: Date?,
    val favourited: Boolean = false,
    val reblogged: Boolean = false,
    val muted: Boolean = false,
    val bookmarked: Boolean = false,
    val content: String,
    val account: Account,
    val emojis: List<CustomEmoji>,
    @SerializedName("media_attachments") val mediaAttachments: List<MediaAttachment>,
    val application: Application? = null,
    val mentions: List<Mention>,
    val pinned: Boolean? = false,
    val tags: List<Tag>,
    val card: Card? = null,
    val reblog: Status? = null,
    val filtered: List<FilterResult>? = null
) {
    data class Application(
        val name: String, val website: String?
    )

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

    data class Mention(
        val id: String, val username: String, val url: String, val acct: String
    )

    data class Tag(
        val name: String, val url: String
    )

    data class Card(
        val url: String,
        val title: String,
        val description: String,
        val type: String,
        val author_name: String,
        val author_url: String,
        val provider_name: String,
        val provider_url: String,
        val html: String,
        val width: Int,
        val height: Int,
        val image: String,
        val embed_url: String,
        val blurhash: String,
    )

    enum class Visibility {
        UNKNOWN,

        /**
         * Visible to everyone, shown in public timelines.
         * */
        @SerializedName("public")
        PUBLIC,

        /**
         * Visible to public, but not included in public timelines.
         * */
        @SerializedName("unlisted")
        UNLISTED,

        /**
         * Visible to followers only, and to any mentioned users.
         * */
        @SerializedName("private")
        PRIVATE,

        /**
         * Visible only to mentioned users.
         * */
        @SerializedName("direct")
        DIRECT,
    }
}

