package com.sjaindl.assistant.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sjaindl.assistant.config.ChatIcon
import com.sjaindl.assistant.ui.components.MessageIcon
import io.github.sjaindl.assistant.generated.resources.Res
import io.github.sjaindl.assistant.generated.resources.assistant_title
import io.github.sjaindl.assistant.generated.resources.back
import io.github.sjaindl.assistant.generated.resources.reset
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssistantAppBar(
    title: StringResource,
    icon: ChatIcon?,
    iconTint: Boolean,
    showResetOption: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = { },
    onResetChat: () -> Unit = { },
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.Bottom) {
                if (icon != null) {
                    MessageIcon(
                        chatIcon = icon,
                        modifier = Modifier
                            .size(size = 24.dp),
                        tint = if (iconTint) colorScheme.onPrimary else Color.Unspecified
                    )

                    Spacer(
                        modifier = Modifier
                            .width(width = 8.dp)
                    )
                }
                Text(
                    text = stringResource(title),
                    color = colorScheme.onPrimary,
                )
            }
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
        actions = {
            if (showResetOption) {
                IconButton(
                    onClick = onResetChat,
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(Res.string.reset),
                        tint = colorScheme.onPrimary,
                    )
                }
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
            icon = ChatIcon.Vector(imageVector = Icons.Default.Title),
            iconTint = true,
            showResetOption = true,
            navigateUp = { },
        )
    }
}
