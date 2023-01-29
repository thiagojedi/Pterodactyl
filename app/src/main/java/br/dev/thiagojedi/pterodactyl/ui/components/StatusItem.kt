package br.dev.thiagojedi.pterodactyl.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.model.mock.ReplyStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.SimpleStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.StatusWithLinkAndHashtags
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.utils.*

@Composable
fun StatusItem(status: Status) {
    val actualStatus = status.reblog ?: status

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RebloggedTag(status = status)
        Card {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AnimatedAsyncImage(
                        model = actualStatus.account.avatar,
                        contentDescription = "Avatar",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .size(44.dp)
                    )
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
    val context = LocalContext.current
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
        IconButton(onClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, status.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(context, shareIntent, null)
        }) {
            Icon(
                Icons.Rounded.Share,
                contentDescription = "Share",
                tint = tint
            )
        }
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

@Composable
fun AccountInfo(account: Account, modifier: Modifier = Modifier) {
    val textStyle = MaterialTheme.typography.titleMedium

    val (annotatedString, inlineContent) = emojify(
        account.display_name,
        account.emojis,
        textStyle.fontSize
    )

    Column(modifier = modifier) {
        Text(
            text = annotatedString,
            inlineContent = inlineContent,
            style = textStyle,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "@${account.acct}",
            style = textStyle,
            fontWeight = FontWeight.Light,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
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