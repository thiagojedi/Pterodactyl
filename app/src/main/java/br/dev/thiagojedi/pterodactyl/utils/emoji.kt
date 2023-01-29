package br.dev.thiagojedi.pterodactyl.utils

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import br.dev.thiagojedi.pterodactyl.data.model.CustomEmoji
import br.dev.thiagojedi.pterodactyl.ui.components.AnimatedAsyncImage
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
) = emojify(AnnotatedString(source), emojis, emojiSize)

fun emojify(
    source: AnnotatedString,
    emojis: List<CustomEmoji>,
    emojiSize: TextUnit = TextUnit.Unspecified,
): Pair<AnnotatedString, Map<String, InlineTextContent>> {
    val annotatedString = annotateEmojis(source, emojis)
    val inlineContent = emojis.map { (shortcode, url) ->
        shortcode to InlineTextContent(
            Placeholder(
                emojiSize,
                emojiSize,
                PlaceholderVerticalAlign.TextCenter
            )
        ) {
            AnimatedAsyncImage(model = url, contentDescription = shortcode)
        }
    }.toMap()

    return annotatedString to inlineContent
}