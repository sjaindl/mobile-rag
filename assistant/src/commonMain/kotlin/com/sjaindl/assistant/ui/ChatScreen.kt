package com.sjaindl.assistant.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.sjaindl.assistant.assistant.generated.resources.Res
import com.sjaindl.assistant.assistant.generated.resources.prompt_label
import com.sjaindl.assistant.config.ChatIcon
import com.sjaindl.assistant.data.remote.model.SourceDocument
import com.sjaindl.assistant.data.remote.model.Tool
import com.sjaindl.assistant.ui.components.ChatInputControl
import com.sjaindl.assistant.ui.components.ChatLoadingScreen
import com.sjaindl.assistant.ui.components.MessageCard
import com.sjaindl.assistant.ui.components.SampleQuestions
import com.sjaindl.assistant.ui.model.ChatMessage
import com.sjaindl.assistant.ui.model.ChatUiState
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    uiState: ChatUiState,
    sampleQuestions: List<String>,
    welcomeMessage: String?,
    assistantIcon: ChatIcon,
    userIcon: ChatIcon,
    messageCharLimit: Int?,
    promptPlaceholder: StringResource,
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
            welcomeMessage?.let {
                item {
                    MessageCard(
                        message = ChatMessage(
                            text = welcomeMessage,
                            isFromUser = false,
                        ),
                        assistantIcon = assistantIcon,
                        userIcon = userIcon,
                    )
                }
            }

            if (uiState.messages.isEmpty()) {
                item {
                    SampleQuestions(
                        sampleQuestions = sampleQuestions,
                        onSendPrompt = onSendPrompt,
                    )
                }
            }

            items(items = uiState.messages) { message ->
                MessageCard(
                    message = message,
                    assistantIcon = assistantIcon,
                    userIcon = userIcon,
                )
            }

            if (uiState.isLoading) {
                item {
                    ChatLoadingScreen()
                }
            }
        }

        ChatInputControl(
            uiState = uiState,
            messageCharLimit = messageCharLimit,
            promptPlaceholder = promptPlaceholder,
            onSendPrompt = onSendPrompt,
        )
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
                        usedTools = listOf(
                            Tool(
                                tool = "Search",
                                toolInput = JsonPrimitive("input"),
                                toolOutput = "output"
                            )
                        ),
                        sourceDocuments = listOf(
                            SourceDocument(
                                pageContent = "pageContent",
                                metadata = JsonPrimitive("metadata")
                            )
                        )
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
            sampleQuestions = listOf(
                "What is the capital of India?",
                "What is the largest planet in our solar system?",
            ),
            welcomeMessage = "This is a welcome message!",
            assistantIcon = ChatIcon.Vector(imageVector = Icons.AutoMirrored.Filled.Chat),
            userIcon = ChatIcon.Vector(imageVector = Icons.Default.Person),
            messageCharLimit = 250,
            promptPlaceholder = Res.string.prompt_label,
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
            sampleQuestions = listOf(
                "What is the capital of India?",
                "What is the largest planet in our solar system?",
            ),
            welcomeMessage = "This is a welcome message!",
            assistantIcon = ChatIcon.Vector(imageVector = Icons.AutoMirrored.Filled.Chat),
            userIcon = ChatIcon.Vector(imageVector = Icons.Default.Person),
            messageCharLimit = 250,
            promptPlaceholder = Res.string.prompt_label,
            onSendPrompt = { },
        )
    }
}
