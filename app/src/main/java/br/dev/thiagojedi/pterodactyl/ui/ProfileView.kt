import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.mock.Account
import br.dev.thiagojedi.pterodactyl.data.model.mock.SimpleStatus
import br.dev.thiagojedi.pterodactyl.ui.actions.ShareAction
import br.dev.thiagojedi.pterodactyl.ui.components.AccountInfo
import br.dev.thiagojedi.pterodactyl.ui.components.StatusItem
import br.dev.thiagojedi.pterodactyl.ui.designSystem.Avatar
import br.dev.thiagojedi.pterodactyl.ui.designSystem.AvatarSize
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme
import br.dev.thiagojedi.pterodactyl.utils.emojify
import br.dev.thiagojedi.pterodactyl.utils.parseMastodonHtml
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(account: Account) {
    val density = LocalDensity.current
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyListState = rememberLazyListState()
    val isCollapsed = remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 1 } }

    Scaffold(Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        Box {
            AsyncImage(
                model = account.header,
                contentDescription = "Header",
                Modifier.matchParentSize(),
                contentScale = ContentScale.FillWidth,
            )
            LargeTopAppBar(title = {
                Column {
                    AnimatedVisibility(
                        visible = isCollapsed.value,
                        enter = slideInVertically { with(density) { 40.dp.roundToPx() } },
                        exit = slideOutVertically { with(density) { 50.dp.roundToPx() } }
                    ) {
                        AccountInfo(account = account)
                    }
                }
            }, scrollBehavior = scrollBehavior, actions = {
                Icon(Icons.Filled.PersonAdd, contentDescription = "Add to followers")
                ShareAction(content = account.url)
            }, colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            )
        }
    }) {
        LazyColumn(
            Modifier.padding(it),
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Avatar(
                    account = account,
                    variant = AvatarSize.Large,
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp), true),
                )
            }
            item { ProfileTitle(account = account) }

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    val style = MaterialTheme.typography.labelSmall
                    Text(text = "Posted ${account.statusesCount}", style = style)
                    Text(text = "Following ${account.followingCount}", style = style)
                    Text(text = "Followed ${account.followersCount}", style = style)
                }
            }

            item {
                ProfileDescription(account = account)
            }

            items(3) {
                StatusItem(status = SimpleStatus)
            }
        }
    }
}

@Composable
private fun ProfileTitle(account: Account) {
    val (annotated, inlineContent) = emojify(account.display_name, account.emojis)
    Text(
        text = annotated,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
private fun ProfileDescription(account: Account) {
    val description = parseMastodonHtml(
        account.note, paragraphStyle = MaterialTheme.typography.bodyMedium.toParagraphStyle()
    )
    val (annotated, inlineContent) = emojify(description, account.emojis)
    Text(text = annotated, inlineContent = inlineContent, modifier = Modifier.padding(16.dp))
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
private fun PreviewProfileView() {
    PterodactylTheme() {
        ProfileView(Account)
    }
}