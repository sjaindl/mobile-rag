package com.sjaindl.assistant

import sjaindl.assistant.firstElement
import sjaindl.assistant.generateFibi
import sjaindl.assistant.secondElement
import kotlin.test.Test
import kotlin.test.assertEquals

class FibiTest {

    @Test
    fun `test 3rd element`() {
        assertEquals(firstElement + secondElement, generateFibi().take(3).last())
    }
}