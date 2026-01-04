package com.sjaindl.assistant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sjaindl.assistant.config.ChatIcon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageIcon(
    chatIcon: ChatIcon,
    modifier: Modifier = Modifier,
) {
    when (chatIcon) {
        is ChatIcon.Drawable -> {
            Image(
                painter = painterResource(resource = chatIcon.drawable),
                contentDescription = null,
                modifier = modifier,
            )
        }

        is ChatIcon.Vector -> {
            Image(
                imageVector = chatIcon.imageVector,
                contentDescription = null,
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
fun MessageIconPreview() {
    MaterialTheme {
        MessageIcon(
            chatIcon = ChatIcon.Vector(imageVector = Icons.AutoMirrored.Filled.Chat),
        )
    }
}