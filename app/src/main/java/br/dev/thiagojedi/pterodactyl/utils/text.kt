package br.dev.thiagojedi.pterodactyl.utils

import android.icu.text.RelativeDateTimeFormatter
import androidx.compose.ui.text.*
import androidx.core.text.parseAsHtml
import br.dev.thiagojedi.pterodactyl.data.model.Status
import java.util.*
import java.util.regex.Pattern

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
    Regex("<p>(.+?)</p>", RegexOption.DOT_MATCHES_ALL)
}
private val aTagPattern by lazy {
    Regex("<a href=\"([^\"]+)\"[^>]*>(<span class=\"(ellipsis)?\">)?([^<]+?)(</span>)?</a>")
}
private val leadingSpacesPattern by lazy {
    Regex("^(\\s+)")
}
const val URLTag = "URL"
const val MentionTag = "MENTION"
const val HashtagTag = "TAG"

fun parseMastodonHtml(
    text: String,
    mentions: List<Status.Mention> = emptyList(),
    linkStyle: SpanStyle = SpanStyle(),
    paragraphStyle: ParagraphStyle = ParagraphStyle(),
): AnnotatedString {
    val cleanText = text
        .replace(invisibleSpanPattern, "")
        .replace(mentionPattern) { match -> "@${match.groups[1]?.value}" }
        .replace(hashtagPattern) { match -> "#${match.groups[1]?.value}" }
    val paragraphMatch = paragraphPattern.findAll(cleanText)
    val count = paragraphMatch.count() - 1
    val paragraphs = buildAnnotatedString {
        paragraphMatch.forEachIndexed { index, matchResult ->
            val substring = matchResult.groups[1]?.value.orEmpty()
            val paragraphWithLinks = buildAnnotatedString {
                val links = aTagPattern.findAll(substring)
                var position = 0

                for (link in links) {
                    val linkUrl = link.groups[1]?.value.orEmpty()
                    val linkText = link.groups[4]?.value
                    val isFullText = link.groups[3]?.value.isNullOrEmpty()
                    val stringBefore =
                        substring.slice(position until link.range.first).parseAsHtml()
                    append(stringBefore)

                        pushStringAnnotation(URLTag, linkUrl)
                        withStyle(linkStyle) {
                            if (isFullText)
                                append(linkText)
                            else // with ellipsis
                                append("$linkText...")
                        }
                        pop()

                    position = link.range.last + 1
                }
                val stringAfter =
                    if (links.none()) substring
                    else substring.slice(position..substring.lastIndex)
                // This is needed because "parseAsHtml" trims leading spaces
                val leadingSpaces = leadingSpacesPattern.find(stringAfter)?.value.orEmpty()

                append(leadingSpaces + stringAfter.parseAsHtml())
            }

            withStyle(paragraphStyle) {
                if (index == count) append(paragraphWithLinks)
                else appendLine(paragraphWithLinks)
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
                    annotation = mention.id,
                    start = match.start(),
                    end = match.end()
                )
            }
        }
        val match = Pattern
            .compile("(?:^|[^/)\\w])#([\\p{L}\\d_]+)", Pattern.CASE_INSENSITIVE)
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

private const val SECOND = 1000
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR

fun Date.fromNow(): String {
    val formatter = RelativeDateTimeFormatter.getInstance()
    val now = Date()
    val diff = now.time - this.time

    return when {
        diff < SECOND -> formatter.format(
            RelativeDateTimeFormatter.Direction.PLAIN,
            RelativeDateTimeFormatter.AbsoluteUnit.NOW
        )
        diff < MINUTE -> formatter.format(
            (diff / SECOND).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.SECONDS
        )
        diff < HOUR -> formatter.format(
            (diff / MINUTE).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.MINUTES
        )
        diff < DAY -> formatter.format(
            (diff / HOUR).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.HOURS
        )
        else -> formatter.format(
            (diff / DAY).toDouble(),
            RelativeDateTimeFormatter.Direction.LAST,
            RelativeDateTimeFormatter.RelativeUnit.DAYS
        )
    }
}
