package com.sjaindl.assistant.domain

import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import kotlinx.coroutines.flow.Flow

interface AssistantRepository {
    fun getCompletion(prompt: String, chatId: String?, streaming: Boolean): Flow<FlowiseResponse>
}
