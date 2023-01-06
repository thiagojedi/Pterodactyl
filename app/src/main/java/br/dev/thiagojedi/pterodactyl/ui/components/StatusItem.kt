package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.parseAsHtml
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.Status
import br.dev.thiagojedi.pterodactyl.utils.emojify
import coil.compose.AsyncImage

@Composable
fun StatusItem(status: Status) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
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

        val (content, inlineContent) = emojify(
            status.content.parseAsHtml().toString(),
            status.emojis,
            16.sp,
            LocalContext.current
        )
        Text(
            text = content,
            inlineContent = inlineContent,
            softWrap = true,
            fontSize = 16.sp,
            lineHeight = 21.sp
        )
    }
}

@Composable
fun AccountName(
    account: Account, fontWeight: FontWeight? = null, fontSize: TextUnit = TextUnit.Unspecified
) {
    val (annotatedString, inlineContent) = emojify(
        account.display_name,
        account.emojis,
        fontSize,
        LocalContext.current
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