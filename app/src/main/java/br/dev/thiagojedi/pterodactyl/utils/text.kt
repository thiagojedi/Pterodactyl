package br.dev.thiagojedi.pterodactyl.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.text.parseAsHtml
import br.dev.thiagojedi.pterodactyl.data.model.Status
import java.util.regex.Pattern

fun parseMastodonHtml(
    text: String,
    mentions: List<Status.Mention> = emptyList(),
    tags: List<Status.Tag> = emptyList(),
    linkStyle: SpanStyle
): AnnotatedString {
    val parseAsHtml = text.parseAsHtml()
    val content = parseAsHtml.toString()

    return buildAnnotatedString {
        append(content)

        mentions.forEach { (username, url) ->
            val match = Pattern.compile("@$username", Pattern.LITERAL).matcher(content)
            while (match.find()) {
                withStyle(linkStyle) {
                    addStringAnnotation(
                        tag = "MENTION",
                        annotation = url,
                        start = match.start(),
                        end = match.end()
                    )
                }
            }
        }

        tags.forEach { (name, url) ->
            val match = Pattern.compile("#$name", Pattern.LITERAL).matcher(content)
            while (match.find()) {
                withStyle(linkStyle) {
                    addStringAnnotation(
                        tag = "HASHTAG",
                        annotation = url,
                        start = match.start(),
                        end = match.end()
                    )
                }
            }
        }
    }
}