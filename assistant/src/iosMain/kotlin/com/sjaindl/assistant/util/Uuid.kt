package com.sjaindl.assistant.util

import platform.Foundation.NSUUID

actual fun generateUuid(): String = NSUUID().UUIDString()
