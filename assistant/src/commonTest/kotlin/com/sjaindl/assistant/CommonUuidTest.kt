package com.sjaindl.assistant

import com.sjaindl.assistant.util.generateUuid
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommonUuidTest {

    @Test
    fun testGenerateUuidNotNull() {
        assertNotNull(generateUuid())
    }
}