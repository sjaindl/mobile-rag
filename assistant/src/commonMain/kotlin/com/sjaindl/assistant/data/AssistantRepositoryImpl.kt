package com.sjaindl.assistant.data

import com.sjaindl.assistant.data.remote.AssistantService
import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import com.sjaindl.assistant.domain.AssistantRepository
import com.sjaindl.assistant.ui.model.ChatMessage
import kotlinx.coroutines.flow.Flow

class AssistantRepositoryImpl(
    private val assistantService: AssistantService,
    private val chatMessageDataSource: ChatMessageDataSource,
) : AssistantRepository {

    override fun getCompletion(prompt: String, chatId: String?, streaming: Boolean): Flow<FlowiseResponse> {
        return assistantService.getCompletion(
            prompt = prompt,
            chatId = chatId,
            streaming = streaming,
        )
    }

    override suspend fun getAll(): List<ChatMessage> {
        return chatMessageDataSource.getAll()
    }

    override suspend fun insert(message: ChatMessage): Long {
        return chatMessageDataSource.insert(message = message)
    }

    override suspend fun update(id: Long, message: ChatMessage) {
        chatMessageDataSource.update(id = id, message = message)
    }

    override suspend fun clear() {
        chatMessageDataSource.clear()
    }
}
