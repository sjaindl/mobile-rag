package com.sjaindl.assistant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sjaindl.assistant.assistant.generated.resources.Res
import com.sjaindl.assistant.assistant.generated.resources.chatbot_icon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatLoadingScreen() {
    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = stringResource(Res.string.chatbot_icon),
            modifier = Modifier
                .size(size = 40.dp)
                .clip(CircleShape)
        )

        Spacer(
            modifier = Modifier
                .size(size = 8.dp)
        )

        JumpingDotsIndicator()
    }
}

@Preview
@Composable
fun ChatLoadingScreenPreview() {
    MaterialTheme {
        ChatLoadingScreen()
    }
}
