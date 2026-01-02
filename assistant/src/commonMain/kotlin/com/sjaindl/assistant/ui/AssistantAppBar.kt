package com.sjaindl.assistant.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sjaindl.assistant.assistant.generated.resources.Res
import com.sjaindl.assistant.assistant.generated.resources.assistant_title
import com.sjaindl.assistant.assistant.generated.resources.back
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantAppBar(
    title: StringResource,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = { },
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(title),
                color = colorScheme.onPrimary,
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {
                    navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back),
                    tint = colorScheme.onPrimary,
                )
            }
        },
        colors = topAppBarColors(
            containerColor = colorScheme.primary,
        ),
    )
}

@Preview
@Composable
fun AssistantAppBarPreview() {
    MaterialTheme {
        AssistantAppBar(
            title = Res.string.assistant_title,
            navigateUp = { },
        )
    }
}
