package br.dev.thiagojedi.pterodactyl.utils

import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp
import br.dev.thiagojedi.pterodactyl.data.model.Status
import java.util.regex.Pattern

private val breakLinePattern by lazy {
    Regex("<br\\s*/?>")
}
private val mentionPattern by lazy {
    Regex("<span class=\"h-card\"><a[^>]+?>@<span>([^<]+?)</span></a></span>")
}
private val hashtagPattern by lazy {
    Regex("<a[^>]+?>#<span>([^<]+?)</span></a>")
}
private val invisibleSpanPattern by lazy {
    Regex("<span class=\"invisible\">[^<]*?</span>")
}
private val paragraphPattern by lazy {
    Pattern.compile("<p>(.+?)</p>", Pattern.DOTALL or Pattern.CASE_INSENSITIVE)
}
private val aTagPattern by lazy {
    Regex("<a href=\"([^\"]+)\"[^>]*><span class=\"(ellipsis)?\">([^<]+?)</span></a>")
}

fun parseMastodonHtml(
    text: String,
    mentions: List<Status.Mention> = emptyList(),
    tags: List<Status.Tag> = emptyList(),
    linkStyle: SpanStyle
): AnnotatedString {
    val cleanText = text
        .replace(breakLinePattern, "\n")
        .replace(invisibleSpanPattern, "")
        .replace(mentionPattern) { match -> "@${match.groups[1]?.value}" }
        .replace(hashtagPattern) { match -> "#${match.groups[1]?.value}" }
    val paragraphMatch = paragraphPattern.matcher(cleanText)
    val paragraphs = buildAnnotatedString {
        while (paragraphMatch.find()) {
            val substring = paragraphMatch.group(1).orEmpty()

            withStyle(
                ParagraphStyle(lineHeight = 21.sp)
            ) {
                appendLine(buildAnnotatedString {
                    val links = aTagPattern.findAll(substring)
                    var position = 0

                    for (link in links) {
                        append(substring.slice(position until link.range.first))

                        withStyle(linkStyle) {
                            pushStringAnnotation("URL", link.groups.get(1)?.value.orEmpty())
                            val s = link.groups.get(3)?.value
                            if (link.groups.get(2)?.value.isNullOrEmpty()) {
                                append(s)
                            } else {
                                append("$s...")
                            }
                        }

                        position = link.range.last + 1
                    }

                    if (!links.none()) {
                        append(substring.slice(position..substring.lastIndex))
                    } else {
                        append(substring)
                    }
                })
            }
        }
    }

    return buildAnnotatedString {
        append(paragraphs)

        mentions.forEach { mention ->
            val match = Pattern
                .compile("@${mention.username}", Pattern.LITERAL or Pattern.CASE_INSENSITIVE)
                .matcher(paragraphs)
            while (match.find()) {
                addStyle(linkStyle, match.start(), match.end())
                addStringAnnotation(
                    tag = "MENTION",
                    annotation = mention.url,
                    start = match.start(),
                    end = match.end()
                )
            }
        }

        tags.forEach { tag ->
            //FixMe: Name doesn't have accents. Doesn't match tags like "#eleição"
            val match = Pattern.compile("#${tag.name}", Pattern.LITERAL or Pattern.CASE_INSENSITIVE)
                .matcher(paragraphs)
            while (match.find()) {
                addStyle(linkStyle, match.start(), match.end())
                addStringAnnotation(
                    tag = "HASHTAG",
                    annotation = tag.url,
                    start = match.start(),
                    end = match.end()
                )
            }
        }
    }
}