package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SmsFailed
import androidx.compose.material.icons.outlined.SmsFailed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.dev.thiagojedi.pterodactyl.ui.theme.PterodactylTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeForm(
    startText: String = "", onPost: (text: String, cw: String) -> Unit, onClose: () -> Unit
) {
    val (text, setText) = remember { mutableStateOf(startText) }
    val (contentWarning, setContentWarning) = remember { mutableStateOf("") }
    val (withContentWarning, setWithContentWarning) = remember { mutableStateOf(false) }

    Scaffold(topBar = {
        ComposeTopBar(onPost = { onPost(text, contentWarning) }, onClose = onClose)
    }) {
        Surface(Modifier.padding(it)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AnimatedVisibility(visible = withContentWarning) {
                    ContentWarningField(onContentWarningChange = setContentWarning)
                }
                TextField(
                    value = text, onValueChange = setText, singleLine = false, supportingText = {
                        Text(
                            "${text.length}/500",
                            textAlign = TextAlign.End,
                        )
                    }, modifier = Modifier.fillMaxWidth(), minLines = 3
                )
                ComposeBottomBar(onContentWarning = setWithContentWarning)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun ContentWarningField(onContentWarningChange: (String) -> Unit = {}) {
    val (cw, setCw) = remember {
        mutableStateOf("")
    }
    TextField(modifier = Modifier.fillMaxWidth(), value = cw, onValueChange = {
        setCw(it)
        onContentWarningChange(it)
    }, label = {
        Text(
            "ContentWarning:", style = MaterialTheme.typography.labelSmall, color = Color.Red
        )
    })
}

@ExperimentalMaterial3Api
@Composable
fun ComposeTopBar(isReply: Boolean = false, onPost: () -> Unit = {}, onClose: () -> Unit) {
    TopAppBar(navigationIcon = {
        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
        }
    }, title = { Text(if (isReply) "Reply" else "Compose") }, actions = {
        Button(onClick = {
            onPost()
        }) {
            Text("Toot")
        }
    })
}

@Composable
fun ComposeBottomBar(onContentWarning: (cw: Boolean) -> Unit = {}) {
    val (cw, setCw) = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { /*TODO*/ }, enabled = false) {
            Icon(imageVector = Icons.Filled.AttachFile, contentDescription = "Attach")
        }
        IconToggleButton(checked = cw, onCheckedChange = {
            setCw(it)
            onContentWarning(it)
        }) {
            Icon(
                imageVector = if (cw) Icons.Filled.SmsFailed else Icons.Outlined.SmsFailed,
                contentDescription = "Content Warning"
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TootComposePreview() {
    PterodactylTheme {
        ComposeForm(onPost = { text, cw -> }, onClose = {})
    }
}