package com.sjaindl.assistant.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import com.sjaindl.assistant.ui.theme.spacing
import io.github.sjaindl.assistant.generated.resources.Res
import io.github.sjaindl.assistant.generated.resources.errorDescription
import io.github.sjaindl.assistant.generated.resources.errorTitle
import io.github.sjaindl.assistant.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onButtonClick: (() -> Unit)? = null,
    title: String = stringResource(Res.string.errorTitle),
    text: String = stringResource(Res.string.errorDescription),
    buttonTitle: String = stringResource(Res.string.retry),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.md, Alignment.CenterVertically),
        modifier = modifier
            .fillMaxSize(),
    ) {
        Image(
            imageVector = Icons.Default.Error,
            contentDescription = null,
            colorFilter = ColorFilter
                .tint(color = colorScheme.error),
        )

        Text(
            text = title,
            style = typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = spacing.md),
        )

        val clipboardManager = LocalClipboardManager.current

        Text(
            text = text,
            style = typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = spacing.md).clickable {
                clipboardManager.setText(AnnotatedString((text)))
            }
        )
        onButtonClick?.let {
            Button(onClick = onButtonClick) {
                Text(
                    text = buttonTitle,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    MaterialTheme {
        ErrorScreen(
            onButtonClick = { },
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}
