package com.thoughtworks.kotlin_basic.util

import org.junit.jupiter.api.Assertions.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ColumnSequenceUtilTest {

    private val columnSequenceUtil = ColumnSequenceUtil()

    @Test
    fun `Converting 702 to Column Indices should return ZZ`() {
        val result = columnSequenceUtil.numberToColumnIndices(702)
        assertEquals("ZZ", result )
    }

    @Test
    fun `Converting 702 to Column Indices should not return AZ`() {
        val result = columnSequenceUtil.numberToColumnIndices(702)
        assertNotEquals("AZ", result )
    }

    @Test
    fun `Converting 15852 to Column Indices should return WKR`() {
        val result = columnSequenceUtil.numberToColumnIndices(15852)
        assertEquals("WKR", result )
    }

    @Test
    fun `Converting 16365 to Column Indices should return XEK`() {
        val result = columnSequenceUtil.numberToColumnIndices(16365)
        assertEquals("XEK", result )
    }

    @Test
    fun `Converting 18278 to Column Indices should return ZZZ`() {
        val result = columnSequenceUtil.numberToColumnIndices(18278)
        assertEquals("ZZZ", result )
    }

    @Test
    fun `Converting 18278 to Column Indices should not return AZZ`() {
        val result = columnSequenceUtil.numberToColumnIndices(18278)
        assertNotEquals("AZZ", result )
    }

    @Test
    fun `Converting 18280 to Column Indices should throw IllegalArgumentException`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            columnSequenceUtil.numberToColumnIndices(18280)
        }

        assertEquals("Input must be between 1 and 18279", exception.message)
    }

}