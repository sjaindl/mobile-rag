package com.sjaindl.assistant.ui.model

import com.sjaindl.assistant.data.remote.model.SourceDocument
import com.sjaindl.assistant.data.remote.model.Tool

data class ChatMessage(
    val id: Long? = null,
    val text: String,
    val isFromUser: Boolean,
    val usedTools: List<Tool>? = null,
    val sourceDocuments: List<SourceDocument>? = null,
    val isTyping: Boolean = false,
)
