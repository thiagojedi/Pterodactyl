package br.dev.thiagojedi.pterodactyl.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.dev.thiagojedi.pterodactyl.ui.components.ComposeForm
import br.dev.thiagojedi.pterodactyl.ui.viewModel.StatusViewModel
import kotlinx.coroutines.launch

@Composable
fun ComposeView(
    replyId: String? = null,
    mentions: List<String> = emptyList(),
    statusViewModel: StatusViewModel = viewModel(),
    onPostSuccess: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    val startText = mentions.map { "@$it" }.joinToString(" ")
    ComposeForm(startText = startText,
        onClose = onClose, onPost = { text, cw ->
            statusViewModel.viewModelScope.launch {
                try {
                    val createStatus = statusViewModel.createStatus(text, cw, inReplyTo = replyId)
                    if (createStatus.isSuccessful) {
                        onPostSuccess()
                    } else {
                        Log.e("ERROR", "ComposeView: ${createStatus.message()}")
                    }
                } catch (error: Exception) {
                    Log.e("ERROR", "ComposeView: ${error.message}")
                }
            }
        })
}