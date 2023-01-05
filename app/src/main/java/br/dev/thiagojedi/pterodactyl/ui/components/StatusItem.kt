package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.dev.thiagojedi.pterodactyl.data.model.Status
import coil.compose.AsyncImage

@Composable
fun StatusItem(status: Status) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        StatusHeader(status = status)

        if (status.spoilerText != null) {
            Text(status.spoilerText)
        }

        Text(text = status.content, softWrap = true, fontSize = 16.sp, lineHeight = 21.sp)

        ImageList(status)
    }
}

@Composable
fun ImageList(status: Status) {
    LazyRow {
        items(status.mediaAttachments.filter { it.type == Status.MediaAttachmentType.IMAGE }) { attachment ->
            AsyncImage(
                model = attachment.previewUrl,
                contentDescription = attachment.description,
            )
        }
    }
}

@Composable
fun StatusHeader(status: Status) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 4.dp)
    ) {
        AsyncImage(
            model = status.account.avatar,
            contentDescription = "Avatar",
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
        )
        Column {
            Text(
                text = status.account.display_name,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
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
}
