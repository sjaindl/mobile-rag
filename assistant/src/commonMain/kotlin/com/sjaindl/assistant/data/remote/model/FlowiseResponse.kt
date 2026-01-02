package com.sjaindl.assistant.data.remote.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("event")
sealed class FlowiseResponse {
    @Serializable
    @SerialName("start")
    data object Start : FlowiseResponse()

    @Serializable
    @SerialName("token")
    data class Token(val data: String) : FlowiseResponse()

    @Serializable
    @SerialName("end")
    data object End : FlowiseResponse()

    @Serializable
    @SerialName("error")
    data class Error(val data: String) : FlowiseResponse()
}
