package com.sjaindl.assistant.config

data class AssistantConfig(
    val provider: Provider,
)

sealed class Provider {
    data class Flowise(val baseUrl: String) : Provider()
}
