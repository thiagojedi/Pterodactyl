package br.dev.thiagojedi.pterodactyl.utils

import android.icu.text.RelativeDateTimeFormatter
import androidx.compose.ui.text.*
import br.dev.thiagojedi.pterodactyl.data.model.Status
import java.util.*
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
    Regex("<a href=\"([^\"]+)\"[^>]*>(<span class=\"(ellipsis)?\">)?([^<]+?)(</span>)?</a>")
}

const val URLTag = "URL"
const val MentionTag = "MENTION"
const val HashtagTag = "TAG"

fun parseMastodonHtml(
    text: String,
    mentions: List<Status.Mention> = emptyList(),
    tags: List<Status.Tag> = emptyList(),
    paragraphStyle: ParagraphStyle,
    linkStyle: SpanStyle,
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

            withStyle(paragraphStyle) {
                appendLine(buildAnnotatedString {
                    val links = aTagPattern.findAll(substring)
                    var position = 0

                    for (link in links) {
                        append(substring.slice(position until link.range.first))

                        withStyle(linkStyle) {
                            pushStringAnnotation(URLTag, link.groups[1]?.value.orEmpty())
                            val linkText = link.groups[4]?.value
                            val isFullText = link.groups[3]?.value.isNullOrEmpty()

                            if (isFullText)
                                append(linkText)
                            else // with ellipsis
                                append("$linkText...")
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
                    tag = MentionTag,
                    annotation = mention.url,
                    start = match.start(),
                    end = match.end()
                )
            }
        }
        val match = Pattern
            .compile("(?:^|[^/)\\w])#([\\p{L}\\p{Digit}_]+)", Pattern.CASE_INSENSITIVE)
            .matcher(paragraphs)

        while (match.find()) {
            addStyle(linkStyle, match.start(), match.end())
            match.group(1)?.let {
                addStringAnnotation(
                    tag = HashtagTag,
                    annotation = it,
                    start = match.start(),
                    end = match.end()
                )
            }
        }
    }
}

fun Date.fromNow(): String {
    val formatter = RelativeDateTimeFormatter.getInstance()
    val now = Date()
    val diff = now.time - this.time

    if (diff < 1000) {
        return formatter.format(
            RelativeDateTimeFormatter.Direction.PLAIN,
            RelativeDateTimeFormatter.AbsoluteUnit.NOW
        )
    }
    if (diff < 60 * 1000) {
        return formatter.format(
            (diff / 1000).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.SECONDS
        )
    } else if (diff < 60 * 60 * 1000) {
        return formatter.format(
            (diff / (60 * 1000)).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.MINUTES
        )
    } else if (diff < 24 * 60 * 60 * 1000) {
        return formatter.format(
            (diff / (60 * 60 * 1000)).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.HOURS
        )
    } else {
        return formatter.format(
            (diff / (24 * 60 * 60 * 1000)).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.DAYS
        )
    }
}
