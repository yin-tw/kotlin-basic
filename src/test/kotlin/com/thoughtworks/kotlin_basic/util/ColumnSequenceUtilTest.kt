package com.thoughtworks.kotlin_basic.util

import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ColumnSequenceUtilTest {

    private val columnSequenceUtil = ColumnSequenceUtil()

    @Test
    fun `Converting 702 to ColumnSequence should return ZZ`() {
        val result = columnSequenceUtil.numberToColumnIndices(702)
        assertEquals("ZZ", result )
    }

    @Test
    fun `Converting 702 to ColumnSequence should not return AZ`() {
        val result = columnSequenceUtil.numberToColumnIndices(702)
        assertNotEquals("AZ", result )
    }

    @Test
    fun `Converting 20000 to ColumnSequence should throw IllegalArgumentException`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            columnSequenceUtil.numberToColumnIndices(18280)
        }

        assertEquals("Input must be between 1 and 18279", exception.message)
    }
}