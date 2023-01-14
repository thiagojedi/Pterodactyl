package br.dev.thiagojedi.pterodactyl.ui.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.dev.thiagojedi.pterodactyl.R
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.data.model.mock.ReplyStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.SimpleStatus
import br.dev.thiagojedi.pterodactyl.data.model.mock.StatusWithLinkAndHashtags
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.utils.emojify
import br.dev.thiagojedi.pterodactyl.utils.parseMastodonHtml
import coil.compose.AsyncImage

@Composable
fun StatusItem(status: Status) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = status.account.avatar,
                contentDescription = "Avatar",
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(44.dp)
            )
            Column {
                AccountName(
                    account = status.account,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "@${status.account.acct}",
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.MoreVert, contentDescription = "Context menu")
        }

        StatusContent(status = status)
        StatusActions(status = status)
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_fluent_arrow_reply_24_regular),
                contentDescription = "Reply",
            )
        }
        IconToggleButton(checked = favorited, onCheckedChange = setFavorited) {
            Icon(
                painter = painterResource(id = if (favorited) R.drawable.ic_fluent_star_24_filled else R.drawable.ic_fluent_star_24_regular),
                contentDescription = "Favorite"
            )
        }
        IconToggleButton(checked = bookmarked, onCheckedChange = setBookmarked) {
            Icon(
                painter = painterResource(id = if (bookmarked) R.drawable.ic_fluent_bookmark_24_filled else R.drawable.ic_fluent_bookmark_24_regular),
                contentDescription = "Boost"
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_fluent_share_24_regular),
                contentDescription = "Share"
            )
        }
    }
}

@Composable
fun StatusContent(status: Status) {
    val context = LocalContext.current

    val (content, inlineContent) = emojify(
        parseMastodonHtml(
            status.content, status.mentions, status.tags, linkStyle = SpanStyle(
                color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold
            )
        ), status.emojis, 16.sp, context
    )
    CustomClickableText(text = content,
        inlineContent = inlineContent,
        softWrap = true,
        fontSize = 16.sp,
        lineHeight = 21.sp,
        onClick = {
            content.getStringAnnotations(it, it).firstOrNull()?.let { stringAnnotation ->
                when (stringAnnotation.tag) {
                    "MENTION" -> Toast.makeText(
                        context, "Mention ${stringAnnotation.item}", Toast.LENGTH_LONG
                    ).show()
                    "HASHTAG" -> Toast.makeText(
                        context, "Hashtag ${stringAnnotation.item}", Toast.LENGTH_LONG
                    ).show()
                    else -> {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW, Uri.parse(stringAnnotation.item)
                            )
                        )
                    }
                }
            }
        })
}

@Composable
fun AccountName(
    account: Account, fontWeight: FontWeight? = null, fontSize: TextUnit = TextUnit.Unspecified
) {
    val (annotatedString, inlineContent) = emojify(
        account.display_name, account.emojis, fontSize, LocalContext.current
    )

    Text(
        text = annotatedString,
        inlineContent = inlineContent,
        fontWeight = fontWeight,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontSize = fontSize
    )
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