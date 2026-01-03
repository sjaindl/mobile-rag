package com.sjaindl.assistant.data.remote.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@OptIn(ExperimentalSerializationApi::class)
@Serializable
sealed class FlowiseResponse {

    @Serializable
    data class FullResponseData(
        val text: String,
        val chatId: String,
        val chatMessageId: String,
        val question: String,
        val sessionId: String,
        val memoryType: String,
        val isStreamValid: Boolean,
    ) : FlowiseResponse()

    @Serializable
    @SerialName("error")
    data class Error(val data: String) : FlowiseResponse()
}
