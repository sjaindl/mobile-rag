package com.sjaindl.assistant.data

import com.sjaindl.assistant.data.remote.AssistantService
import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import com.sjaindl.assistant.domain.AssistantRepository
import kotlinx.coroutines.flow.Flow

class AssistantRepositoryImpl(
    private val assistantService: AssistantService
) : AssistantRepository {

    override fun getCompletion(prompt: String, chatId: String?, streaming: Boolean): Flow<FlowiseResponse> {
        return assistantService.getCompletion(
            prompt = prompt,
            chatId = chatId,
            streaming = streaming,
        )
    }
}
