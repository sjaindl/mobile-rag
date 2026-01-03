package com.sjaindl.assistant

import com.sjaindl.assistant.util.generateUuid
import kotlin.test.Test
import kotlin.test.assertNotNull

class CommonUuidTest {

    @Test
    fun `test generateUuid not null`() {
        assertNotNull(generateUuid())
    }
}