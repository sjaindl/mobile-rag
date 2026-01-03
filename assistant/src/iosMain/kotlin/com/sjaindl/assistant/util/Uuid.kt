package com.sjaindl.assistant.util

import platform.Foundation.NSUUID

actual fun generateUUID(): String = NSUUID().UUIDString()
