package br.dev.thiagojedi.pterodactyl.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.dev.thiagojedi.pterodactyl.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PteroTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    PteroTopBar()
}