package br.dev.thiagojedi.pterodactyl.utils

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import br.dev.thiagojedi.pterodactyl.data.model.CustomEmoji
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import java.util.regex.Pattern

private fun annotateEmojis(source: AnnotatedString, emojis: List<CustomEmoji>) =
    buildAnnotatedString {
        append(source)
        emojis.forEach { (shortcode) ->
            val matcher = Pattern.compile(":$shortcode:", Pattern.LITERAL).matcher(source)
            while (matcher.find()) {
                addStringAnnotation(
                    "androidx.compose.foundation.text.inlineContent",
                    shortcode,
                    matcher.start(),
                    matcher.end()
                )
            }
        }
    }

fun emojify(
    source: String,
    emojis: List<CustomEmoji>,
    emojiSize: TextUnit = TextUnit.Unspecified,
    context: Context
) = emojify(AnnotatedString(source), emojis, emojiSize, context)

fun emojify(
    source: AnnotatedString,
    emojis: List<CustomEmoji>,
    emojiSize: TextUnit = TextUnit.Unspecified,
    context: Context
): Pair<AnnotatedString, Map<String, InlineTextContent>> {
    val annotatedString = annotateEmojis(source, emojis)
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    val inlineContent = emojis.map { (shortcode, url) ->
        shortcode to InlineTextContent(
            Placeholder(
                emojiSize,
                emojiSize,
                PlaceholderVerticalAlign.TextCenter
            )
        ) {
            AsyncImage(model = url, contentDescription = shortcode, imageLoader = imageLoader)
        }
    }.toMap()

    return annotatedString to inlineContent
}