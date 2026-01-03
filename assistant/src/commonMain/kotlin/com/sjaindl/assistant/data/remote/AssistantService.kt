package com.sjaindl.assistant.data.remote

import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import kotlinx.coroutines.flow.Flow

interface AssistantService {
    fun getCompletion(prompt: String, chatId: String?, streaming: Boolean): Flow<FlowiseResponse>
}
