package com.sjaindl.assistant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.sjaindl.assistant.ui.components.ChatInputControl
import com.sjaindl.assistant.ui.components.ChatLoadingScreen
import com.sjaindl.assistant.ui.model.ChatMessage
import com.sjaindl.assistant.ui.model.ChatUiState
import com.sjaindl.assistant.ui.components.MessageCard
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    uiState: ChatUiState,
    onSendPrompt: (String) -> Unit,
) {
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages, uiState.isLoading) {
        if (uiState.messages.isNotEmpty() || uiState.isLoading) {
            listState.animateScrollToItem(index = listState.layoutInfo.totalItemsCount)
        }
    }

    uiState.error?.let {
        ErrorScreen(
            modifier = modifier,
            text = it,
        )
    } ?: Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f),
        ) {
            items(items = uiState.messages) { message ->
                MessageCard(message = message)
            }

            if (uiState.isLoading) {
                item {
                    ChatLoadingScreen()
                }
            }
        }

        ChatInputControl(uiState = uiState, onSendPrompt = onSendPrompt)
    }
}

@Preview(name = "Chat Screen with messages")
@Composable
fun ChatScreenPreview() {
    MaterialTheme {
        ChatScreen(
            uiState = ChatUiState(
                isLoading = false,
                messages = listOf(
                    ChatMessage(
                        text = "Hello",
                        isFromUser = true,
                    ),
                    ChatMessage(
                        text = "Hello! How can I help you?",
                        isFromUser = false,
                    ),
                    ChatMessage(
                        text = "Who are the bosses of the club?",
                        isFromUser = true,
                    ),
                    ChatMessage(
                        text = "The bosses are top secret!",
                        isFromUser = false,
                    ),
                    ChatMessage(
                        text = "And what is the next event?",
                        isFromUser = true,
                    ),
                    ChatMessage(
                        text = "The next event is ",
                        isFromUser = false,
                        isTyping = true,
                    ),
                )
            ),
            onSendPrompt = { },
        )
    }
}

@Preview(name = "Chat Screen empty")
@Composable
fun ChatScreenEmptyPreview() {
    MaterialTheme {
        ChatScreen(
            uiState = ChatUiState(
                isLoading = false,
                messages = emptyList(),
            ),
            onSendPrompt = { },
        )
    }
}
