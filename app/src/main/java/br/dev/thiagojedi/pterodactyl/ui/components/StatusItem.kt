package br.dev.thiagojedi.pterodactyl.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import br.dev.thiagojedi.pterodactyl.data.model.mock.RebloggedStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.ReplyStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.SimpleStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.StatusWithLinkAndHashtags
import br.dev.thiagojedi.pterodactyl.ui.actions.*
import br.dev.thiagojedi.pterodactyl.ui.designSystem.Avatar
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.utils.*

@Composable
fun StatusItem(
    status: Status,
    onUserClick: (id: String) -> Unit = {},
    onReplyClick: (id: String, mentions: List<String>) -> Unit = { id, mentions -> }
) {
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
                        Avatar(account = actualStatus.account, modifier = Modifier.clickable {
                            onUserClick(actualStatus.account.id)
                        })
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
                    StatusContent(status = actualStatus, onMentionClick = onUserClick)
                    // TODO: StatusMedia(status = actualStatus)
                    StatusActions(
                        status = actualStatus,
                        onReplyClick = {
                            onReplyClick(
                                actualStatus.id,
                                listOf(actualStatus.account.acct).plus(actualStatus.mentions.map { it.acct })
                            )
                        })
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
fun StatusActions(status: Status, onReplyClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        ReplyAction(onClick = onReplyClick)
        ReblogAction(status = status)
        FavouriteAction(status = status)
        BookmarkAction(status = status)
        ShareAction(content = status.url, tint = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun StatusContent(status: Status, onMentionClick: (String) -> Unit = {}) {
    val highlightStyle =
        SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
    val textStyle = MaterialTheme.typography.bodyMedium
    val paragraphStyle = textStyle.toParagraphStyle()
    val linkStyle = textStyle.toSpanStyle().plus(highlightStyle)
    val mastodonHtml = parseMastodonHtml(
        status.content,
        status.mentions,
        linkStyle = linkStyle,
        paragraphStyle = paragraphStyle
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
                    MentionTag -> onMentionClick(annotation.item)
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
private fun PreviewSimpleStatusItem() {
    PterodactylTheme {
        StatusItem(status = SimpleStatus)
    }
}

@Preview
@Composable
private fun PreviewReplyStatusItem() {
    PterodactylTheme {
        StatusItem(status = ReplyStatus)
    }
}

@Preview
@Composable
private fun PreviewStatusWithHastagsAndLink() {
    PterodactylTheme {
        StatusItem(status = StatusWithLinkAndHashtags)
    }
}

@Preview
@Composable
private fun PreviewRebloggedStatus() {
    PterodactylTheme {
        StatusItem(status = RebloggedStatus)
    }
}