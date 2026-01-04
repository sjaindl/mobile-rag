package com.sjaindl.assistant.domain.usecase

import com.sjaindl.assistant.domain.AssistantRepository
import com.sjaindl.assistant.ui.model.ChatMessage

class InsertChatMessageUseCase(private val repository: AssistantRepository) {
    suspend operator fun invoke(message: ChatMessage) = repository.insert(message = message)
}
