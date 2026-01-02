package com.sjaindl.assistant.ui.model

data class ChatUiState(
    val isLoading: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
    val error: String? = null,
)
