package com.sjaindl.assistant.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sjaindl.assistant.ui.model.ChatMessage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessageCard(
    message: ChatMessage,
) {

    Card(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = if (message.isFromUser) {
                    Icons.Default.VerifiedUser
                } else {
                    Icons.AutoMirrored.Filled.Chat
                },
                contentDescription = null,
                modifier = Modifier
                    .size(size = 40.dp)
                    .clip(CircleShape)
                    .align(Alignment.Top),
            )

            Spacer(
                modifier = Modifier
                    .size(size = 8.dp),
            )

            Column {
                Text(text = message.text)

                if (message.isTyping) {
                    Spacer(
                        modifier = Modifier
                            .size(size = 16.dp)
                    )

                    JumpingDotsIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
fun MessageCardPreview() {
    MaterialTheme {
        MessageCard(
            message = ChatMessage(
                text = "This is a preview with a very long text to see how it looks like",
                isFromUser = false
            )
        )
    }
}
