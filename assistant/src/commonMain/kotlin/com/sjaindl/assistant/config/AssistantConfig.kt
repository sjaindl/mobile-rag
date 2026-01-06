package com.sjaindl.assistant.config

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.sjaindl.assistant.generated.resources.Res
import io.github.sjaindl.assistant.generated.resources.prompt_label
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class AssistantConfig(
    val provider: Provider,
    val appBarTitle: StringResource,
    val streaming: Boolean = true,
    val streamingDelayMilliseconds: Long = 4L,
    val showTools: Boolean = true,
    val showSourceDocuments: Boolean = true,
    val welcomeMessage: StringResource? = null,
    val sampleQuestions: List<StringResource> = emptyList(),
    val persistMessages: Boolean = true,
    val resetOption: Boolean = true,
    val assistantIcon: ChatIcon = ChatIcon.Vector(imageVector = Icons.AutoMirrored.Filled.Chat),
    val userIcon: ChatIcon = ChatIcon.Vector(imageVector = Icons.Default.Person),
    val promptPlaceholder: StringResource = Res.string.prompt_label,
    val messageCharLimit: Int = 250,
)

sealed class Provider {
    data class Flowise(
        val baseUrl: String,
        val apiKey: String? = null,
    ) : Provider()
}

sealed class ChatIcon {
    data class Vector(val imageVector: ImageVector) : ChatIcon()
    data class Drawable(val drawable: DrawableResource) : ChatIcon()
}
