package br.dev.thiagojedi.pterodactyl.ui.designSystem

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.data.model.Account
import br.dev.thiagojedi.pterodactyl.data.model.mock.Account
import coil.compose.AsyncImage

@Composable
fun Avatar(
    account: Account,
    modifier: Modifier = Modifier,
    animated: Boolean = true,
    variant: AvatarSize = AvatarSize.Medium,
    shape: Shape = RoundedCornerShape(12.dp),
    borderWidth: Dp = 0.dp
) {
    AsyncImage(
        model = if (animated) account.avatar else account.avatarStatic,
        contentDescription = "User avatar",
        modifier = modifier
            .clip(shape)
            .size(
                when (variant) {
                    AvatarSize.Small -> 44.dp
                    AvatarSize.Medium -> 44.dp
                    AvatarSize.Large -> 120.dp
                }
            )
    )
}

enum class AvatarSize {
    Small,
    Medium,
    Large
}

@Preview
@Composable
private fun AvatarPreview() {
    Avatar(account = Account)
}