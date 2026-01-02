package com.sjaindl.assistant.ui.model

data class ChatMessage(
    val id: Long? = null,
    val text: String,
    val isFromUser: Boolean,
    val isTyping: Boolean = false,
)
