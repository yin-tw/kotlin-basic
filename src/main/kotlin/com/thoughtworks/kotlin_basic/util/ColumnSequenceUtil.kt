package com.thoughtworks.kotlin_basic.util

class ColumnSequenceUtil {
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun numberSequenceToColumnIndexLabels(startingSequence: Int, count: Int): Array<String>{
        require( startingSequence > 0 && count > 0 ) { "Starting sequence number and result count must be greater than zero"}
        require( startingSequence + count - 1 <= 18278 ) {"Starting and ending sequence numbers must be between 1 and 18278"}

        val columnIndexLabels = arrayListOf<String>()

        for (i in 0..count - 1) {
                columnIndexLabels.add(numberToColumnIndices(startingSequence + i))
        }

        return columnIndexLabels.toTypedArray()
    }

    fun numberToColumnIndices(columnNumber: Int) = when {
        columnNumber <= 26 -> calculateOneChar(columnNumber).toString()
        columnNumber <= 702 -> calculateTwoChar(columnNumber)
        columnNumber <= 18278 -> calculateThreeChar(columnNumber)
        else -> throw IllegalArgumentException("Input must be between 1 and 18278")
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

    private fun calculateThreeChar (columnNumber: Int):String {
        val multipleOf676 = columnNumber / 676

        var firstCharNumber = if (multipleOf676  > 26 ) multipleOf676 % 26 else multipleOf676
        val secondAndThirdChar = calculateTwoChar(columnNumber)

        if (secondAndThirdChar[0] == 'Z') firstCharNumber -= 1

        return "${calculateOneChar(firstCharNumber)}${secondAndThirdChar}"
    }

}