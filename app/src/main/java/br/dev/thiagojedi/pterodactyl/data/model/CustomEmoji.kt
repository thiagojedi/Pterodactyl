package br.dev.thiagojedi.pterodactyl.data.model

import com.google.gson.annotations.SerializedName

data class CustomEmoji(
    val shortcode: String,
    val url: String,
    @SerializedName("static_url") val staticUrl: String,
    @SerializedName("visible_in_picker") val visibleInPicker: Boolean,
    val category: String,
)