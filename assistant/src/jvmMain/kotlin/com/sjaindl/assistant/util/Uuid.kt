package com.sjaindl.assistant.util

import java.util.UUID

actual fun generateUuid(): String = UUID.randomUUID().toString()
