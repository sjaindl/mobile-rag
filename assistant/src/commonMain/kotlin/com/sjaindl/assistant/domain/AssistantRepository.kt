package com.sjaindl.assistant.domain

import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import com.sjaindl.assistant.ui.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface AssistantRepository {
    fun getCompletion(prompt: String, chatId: String?, streaming: Boolean): Flow<FlowiseResponse>

    suspend fun getAll(): List<ChatMessage>

    suspend fun insert(message: ChatMessage): Long

    suspend fun update(id: Long, message: ChatMessage)

    suspend fun clear()
}
