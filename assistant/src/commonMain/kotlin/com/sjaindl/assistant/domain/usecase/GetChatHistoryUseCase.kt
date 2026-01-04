package com.sjaindl.assistant.domain.usecase

import com.sjaindl.assistant.domain.AssistantRepository

class GetChatHistoryUseCase(private val repository: AssistantRepository) {
    suspend operator fun invoke() = repository.getAll()
}
