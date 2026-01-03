package com.sjaindl.assistant.config

import org.jetbrains.compose.resources.StringResource

data class AssistantConfig(
    val provider: Provider,
    val appBarTitle: StringResource,
    val streaming: Boolean = true,
    val streamingDelayMilliseconds: Long = 4L,
)

sealed class Provider {
    data class Flowise(val baseUrl: String) : Provider()
}
