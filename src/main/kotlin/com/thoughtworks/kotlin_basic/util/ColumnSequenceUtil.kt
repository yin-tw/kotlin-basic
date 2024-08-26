package com.thoughtworks.kotlin_basic.util

class ColumnSequenceUtil {
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun numberToColumnIndices(columnNumber: Int) = when {
        columnNumber <= 26 -> calculateOneChar(columnNumber).toString()
        columnNumber <= 702 -> calculateTwoChar(columnNumber)
        else -> throw IllegalArgumentException("Input must be between 1 and 18279")
    }

    private fun calculateOneChar(columnNumber: Int): Char = if (columnNumber == 0) 'Z' else alphabet[columnNumber - 1]

    private fun calculateTwoChar (columnNumber: Int):String {
        val multipleOf26 = columnNumber / 26
        val modulusOf26 = columnNumber % 26

        var firstCharNumber = if (multipleOf26 > 26) multipleOf26 % 26 else multipleOf26
        val secondCharNumber = modulusOf26

        if (secondCharNumber == 0) firstCharNumber -= 1

        return "${calculateOneChar(firstCharNumber)}${calculateOneChar(secondCharNumber)}"
    }

}