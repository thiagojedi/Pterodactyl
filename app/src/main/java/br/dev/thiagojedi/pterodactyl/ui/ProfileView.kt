import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.mock.Account
import br.dev.thiagojedi.pterodactyl.ui.components.AnimatedAsyncImage
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.utils.emojify
import br.dev.thiagojedi.pterodactyl.utils.parseMastodonHtml

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(account: Account) {
    Column {
        Box {
            AnimatedAsyncImage(
                model = account.header,
                contentDescription = "Profile header",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.matchParentSize()
            )

            MediumTopAppBar(
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
                title = {
                    ProfileTitle(account = account)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent)
            )
        }
        ProfileDescription(account = account)
    }
}

@Composable
private fun ProfileTitle(account: Account) {
    val (annotated, inlineContent) = emojify(account.display_name, account.emojis)
    Text(
        text = annotated,
        inlineContent = inlineContent,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun ProfileDescription(account: Account) {
    val description = parseMastodonHtml(
        account.note,
        paragraphStyle = MaterialTheme.typography.bodyMedium.toParagraphStyle()
    )
    val (annotated, inlineContent) = emojify(description, account.emojis)
    Text(text = annotated, inlineContent = inlineContent)
}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewProfileView() {
    PterodactylTheme() {
        ProfileView(Account)
    }
}