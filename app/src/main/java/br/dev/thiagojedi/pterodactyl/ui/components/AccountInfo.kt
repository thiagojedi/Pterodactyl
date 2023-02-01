package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.utils.emojify

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