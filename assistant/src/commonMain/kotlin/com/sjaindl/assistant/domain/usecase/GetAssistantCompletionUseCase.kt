package com.sjaindl.assistant.domain.usecase

import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import com.sjaindl.assistant.domain.AssistantRepository
import kotlinx.coroutines.flow.Flow

class GetAssistantCompletionUseCase(private val repository: AssistantRepository) {
    operator fun invoke(prompt: String, chatId: String?): Flow<FlowiseResponse> {
        return repository.getCompletion(prompt, chatId)
    }
}
