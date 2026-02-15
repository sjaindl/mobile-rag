package com.sjaindl.assistant

import com.sjaindl.assistant.util.generateUuid
import org.junit.Test
import kotlin.test.assertNotNull

class AndroidUuidTest {

    @Test
    fun testGenerateUuidNotNull() {
        assertNotNull(generateUuid())
    }
}