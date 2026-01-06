package com.sjaindl.assistant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.sjaindl.assistant.config.ChatIcon
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageIcon(
    chatIcon: ChatIcon,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
) {
    val colorFilter = when {
        tint.isSpecified -> ColorFilter.tint(tint)
        else -> null
    }

    when (chatIcon) {
        is ChatIcon.Drawable -> {
            Image(
                painter = painterResource(resource = chatIcon.drawable),
                contentDescription = null,
                modifier = modifier,
                colorFilter = colorFilter,
            )
        }

        is ChatIcon.Vector -> {
            Image(
                imageVector = chatIcon.imageVector,
                contentDescription = null,
                modifier = modifier,
                colorFilter = colorFilter,
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

private val Color.isSpecified: Boolean
    get() = this != Color.Unspecified
