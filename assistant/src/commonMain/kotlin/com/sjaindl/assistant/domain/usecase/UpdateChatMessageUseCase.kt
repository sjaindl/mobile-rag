package com.sjaindl.assistant.domain.usecase

import com.sjaindl.assistant.domain.AssistantRepository
import com.sjaindl.assistant.ui.model.ChatMessage

class UpdateChatMessageUseCase(private val repository: AssistantRepository) {
    suspend operator fun invoke(id: Long, message: ChatMessage) = repository.update(
        id = id,
        message = message,
    )
}
