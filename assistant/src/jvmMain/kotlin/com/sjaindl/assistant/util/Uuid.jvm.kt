package com.sjaindl.assistant.util

import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()
