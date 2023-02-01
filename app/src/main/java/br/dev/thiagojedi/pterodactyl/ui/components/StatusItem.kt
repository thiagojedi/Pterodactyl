package br.dev.thiagojedi.pterodactyl.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.dev.thiagojedi.pterodactyl.data.model.Filter
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.model.mock.ReplyStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.SimpleStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.StatusWithLinkAndHashtags
import br.dev.thiagojedi.pterodactyl.ui.actions.ShareAction
import br.dev.thiagojedi.pterodactyl.ui.designSystem.Avatar
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.utils.*

@Composable
fun StatusItem(status: Status) {
    val actualStatus = status.reblog ?: status
    val density = LocalDensity.current

    val (filtered, setFiltered) = remember {
        mutableStateOf((actualStatus.filtered?.size ?: 0) > 0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AnimatedVisibility(
            visible = !filtered,
            enter = slideInVertically { with(density) { 40.dp.roundToPx() } }
        ) {
            RebloggedTag(status = status)
        }
        Card {
            if (filtered) FilteredTag(status = actualStatus, onClick = { setFiltered(false) })
            AnimatedVisibility(
                visible = !filtered,
                enter = slideInVertically() + expandVertically(expandFrom = Alignment.Top) + fadeIn(
                    initialAlpha = 0.3f
                ),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Avatar(account = actualStatus.account)
                        AccountInfo(
                            account = actualStatus.account,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(
                            modifier = Modifier
                        )
                        Text(
                            text = actualStatus.createdAt.fromNow(),
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                    }
                    StatusContent(status = actualStatus)
                    // TODO: StatusMedia(status = actualStatus)
                    StatusActions(status = actualStatus)
                }
            }
        }
    }
}

@Composable
fun FilteredTag(status: Status, onClick: () -> Unit) {
    val filterTitles =
        status.filtered!!.filter { it.filter.filterAction != Filter.FilterAction.HIDE }
            .joinToString { it.filter.title }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Hidden by filters: $filterTitles",
            style = MaterialTheme.typography.labelSmall
        )

        ClickableText(
            onClick = { onClick() },
            text = AnnotatedString("Show anyway"),
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun StatusActions(status: Status) {
    val (favorited, setFavorited) = remember {
        mutableStateOf(status.favourited)
    }
    val (bookmarked, setBookmarked) = remember {
        mutableStateOf(status.bookmarked)
    }
    val (boosted, setBoosted) = remember {
        mutableStateOf(status.reblogged)
    }
    val tint = MaterialTheme.colorScheme.onSurface
    val selectedTint = MaterialTheme.colorScheme.primary
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { }, enabled = false) {
            Icon(
                Icons.Filled.Reply,
                contentDescription = "Reply",
            )
        }
        IconToggleButton(checked = boosted, onCheckedChange = setBoosted) {
            Icon(
                if (boosted) Icons.Rounded.RepeatOn else Icons.Rounded.Repeat,
                contentDescription = "Boost",
                tint = if (boosted) selectedTint else tint
            )
        }
        IconToggleButton(checked = favorited, onCheckedChange = setFavorited) {
            Icon(
                if (favorited) Icons.Rounded.Star else Icons.Rounded.StarBorder,
                contentDescription = "Favorite",
                tint = if (favorited) selectedTint else tint
            )
        }
        IconToggleButton(checked = bookmarked, onCheckedChange = setBookmarked) {
            Icon(
                if (bookmarked) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                contentDescription = "Boost",
                tint = if (bookmarked) selectedTint else tint
            )
        }
        ShareAction(content = status.url, tint = tint)
        IconButton(onClick = { }, enabled = false) {
            Icon(Icons.Rounded.MoreHoriz, contentDescription = "More actions")
        }
    }
}

@Composable
fun StatusContent(status: Status) {
    val highlightStyle =
        SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
    val textStyle = MaterialTheme.typography.bodyMedium
    val paragraphStyle = textStyle.toParagraphStyle()
    val linkStyle = textStyle.toSpanStyle().plus(highlightStyle)
    val mastodonHtml = parseMastodonHtml(
        status.content,
        status.mentions,
        paragraphStyle = paragraphStyle,
        linkStyle = linkStyle
    )
    val context = LocalContext.current

    val (content, inlineContent) = emojify(mastodonHtml, status.emojis, 16.sp)

    CustomClickableText(
        text = content,
        color = MaterialTheme.colorScheme.onSurface,
        inlineContent = inlineContent,
        softWrap = true,
        style = textStyle,
        onClick = {
            content.getStringAnnotations(it, it).firstOrNull()?.let { annotation ->
                when (annotation.tag) {
                    MentionTag -> Toast.makeText(
                        context,
                        "Mention ${annotation.item}",
                        Toast.LENGTH_LONG
                    ).show()
                    HashtagTag -> Toast.makeText(
                        context,
                        "Hashtag ${annotation.item}",
                        Toast.LENGTH_LONG
                    ).show()
                    URLTag -> {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(annotation.item)
                            )
                        )
                    }
                }
            }
        })
}

@Composable
fun RebloggedTag(status: Status) {
    if (status.reblog != null) {
        val textStyle = MaterialTheme.typography.labelMedium
        val account = status.account
        val (annotatedString, inlineContent) = emojify(
            "Reblogged by ${account.display_name}",
            account.emojis,
            textStyle.fontSize
        )

        Text(text = annotatedString, inlineContent = inlineContent, style = textStyle)
    }
}

@Preview
@Composable
fun PreviewSimpleStatusItem() {
    PterodactylTheme {
        StatusItem(status = SimpleStatus)
    }
}

@Preview
@Composable
fun PreviewReplyStatusItem() {
    PterodactylTheme {
        StatusItem(status = ReplyStatus)
    }
}

@Preview
@Composable
fun PreviewStatusWithHastagsAndLink() {
    PterodactylTheme {
        StatusItem(status = StatusWithLinkAndHashtags)
    }
}