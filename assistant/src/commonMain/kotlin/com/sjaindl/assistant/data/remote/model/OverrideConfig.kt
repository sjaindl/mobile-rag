package com.sjaindl.assistant.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class OverrideConfig(
    val returnSourceDocuments: Boolean? = true,
)
