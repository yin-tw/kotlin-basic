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
        assertNotEquals("AZ", result )
        assertEquals("ZZ", result )
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
        assertNotEquals("AZZ", result )
        assertEquals("ZZZ", result )
    }

    @Test
    fun `Converting 18280 to Column Indices should throw IllegalArgumentException`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            columnSequenceUtil.numberToColumnIndices(18280)
        }

        assertEquals("Input must be between 1 and 18278", exception.message)
    }

    @Test
    fun `Converting starting sequence number 26 and for 3 results should return labels of Z, AA, AB`() {
        val result = columnSequenceUtil.numberSequenceToColumnIndexLabels(26, 3)

        assertEquals(true, result.isArrayOf<String>())
        assertEquals(3, result.size)
        assertEquals("Z", result[0])
        assertEquals("AA", result[1])
        assertEquals("AB", result[2])
    }

    @Test
    fun `Converting starting sequence number 1 and for 2 results should return labels of A, B`() {
        val result = columnSequenceUtil.numberSequenceToColumnIndexLabels(1, 2)

        assertEquals(true, result.isArrayOf<String>())
        assertEquals(2, result.size)
        assertEquals("A", result[0])
        assertEquals("B", result[1])
    }

    @Test
    fun `Converting starting sequence number 18278 and for 1 results should return label AAA`() {
        val result = columnSequenceUtil.numberSequenceToColumnIndexLabels(18278, 1)

        assertEquals(true, result.isArrayOf<String>())
        assertEquals(1, result.size)
        assertEquals("ZZZ", result[0])
    }

    @Test
    fun `Converting starting sequence number 18278 and for 2 results should throw exception`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            columnSequenceUtil.numberSequenceToColumnIndexLabels(18278, 2)
        }

        assertEquals("Starting and ending sequence numbers must be between 1 and 18278", exception.message)
    }

    @Test
    fun `Converting starting sequence number -2 and for 2 results should throw exception`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            columnSequenceUtil.numberSequenceToColumnIndexLabels(-2, 2)
        }

        assertEquals("Starting sequence number and result count must be greater than zero", exception.message)
    }

    @Test
    fun `Converting starting sequence number 55 and for -2 results should throw exception`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            columnSequenceUtil.numberSequenceToColumnIndexLabels(55, -2)
        }

        assertEquals("Starting sequence number and result count must be greater than zero", exception.message)
    }

}